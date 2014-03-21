package com.huayuan.ocr;

import com.huayuan.utils.FileUtils;
import com.huayuan.utils.ImageUtils;

import java.io.IOException;

/**
 * 读取省份证信息 1. 姓名 2. 民族 3. 地址 4. 省份证号码
 *
 * 本地需要安装 ImageMagick 和 OCR
 *
 * @author Administrator
 */
public class ReadCard {

    private String im = "C:/Program Files/ImageMagick-6.8.8-Q8/convert.exe";
    private String ocr = "C:/Program Files (x86)/Tesseract-OCR/tesseract.exe";

    public static void main(String[] args) {
        String src = "E:/world/view/ord/test/card1.png";
        String tag = "E:/world/view/ord/test/carda.png";

        ReadCard rc = new ReadCard();
        //图片缩放
        String rcmd = rc.resize(tag, src, 700, 340);
        rc.runCmd(rcmd);
		//姓名
		String name = rc.readCard(src, tag, 110, 30, 180, 55);
		//民族
		String nation = rc.readCard(src, tag, 262, 80, 80, 50);
		//地址
		String addr = rc.readCard(src, tag, 110, 160, 350, 100);
		//身份证号码
        String num = rc.readCard(src, tag, 210, 250, 500, 100);

		System.out.println(name + ":" + nation + ":" + addr + ":" + num);

    }

    /**
     * 执行命令
     *
     * @param cmd
     */
    public void runCmd(String cmd) {
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(cmd);
            int result = proc.waitFor();
            if (result != 0) {
                String msg = "";
                switch (result) {
                    case 1:
                        msg = "Errors accessing files. There may be spaces in your image's filename.";
                        break;
                    case 29:
                        msg = "Cannot recognize the image or its selected region.";
                        break;
                    case 31:
                        msg = "Unsupported image format.";
                        break;
                    default:
                        msg = "Errors occurred.";
                }
                throw new RuntimeException(msg);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片缩放
     *
     * @param src
     * @param tag
     * @return
     */
    public String resize(String src, String tag, int x, int y) {
        return im + " -resize " + x + "x" + y + "! " + src + " " + tag;
    }

    /**
     * 灰度化命令
     *
     * @param src
     * @param tag
     * @return
     */
    public String grayCmd(String src, String tag) {
        return im + " " + src + " -set colorspace RGB -colorspace gray " + tag;
    }

    /**
     * 将图片转换为高质图片
     *
     * @param src
     * @param tag
     * @return
     */
    public String quality(String src, String tag) {

        return im + " " + src
                + " -colorspace RGB +sigmoidal-contrast 11.6933 -define "
                + "filter:filter=Sinc -define filter:window=Jinc -define "
                + "filter:lobes=3 -resize 600% -sigmoidal-contrast 11.6933 "
                + "-colorspace sRGB " + tag;
    }

    /**
     * 图片识别
     *
     * @param src
     * @param tag
     * @return
     */
    public String tesseract(String src, String tag) {
        return ocr + " " + src + " " + tag + " -l chi_sim -psd 7 nobatch";
    }

    public String readCard(String src, String tag, int x, int y, int width, int height) {
        //获取图片后缀
        if (src == null || src == "") {
            return "";
        }
        String postfix = src.substring(src.lastIndexOf("."), src.length());
        String tmp = "E:/temp" + postfix;

        ReadCard rc = new ReadCard();
        // 切图
        ImageUtils.cutImage(src, tmp, x, y, x + width, y + height);
        // 灰度化
        String gcmd = rc.grayCmd(tmp, tag);
        rc.runCmd(gcmd);
        // 提高图片质量
        String qcmd = rc.quality(tag, tmp);
        rc.runCmd(qcmd);

        // 图片识别
        String tcmd = rc.tesseract(tmp, tag);
        rc.runCmd(tcmd);

        //读取结果
        String result = FileUtils.readFile(tag + ".txt");
        //删除临时文件
        FileUtils.delFile(tmp);
        FileUtils.delFile(tag + ".txt");

        return result;
    }
}
