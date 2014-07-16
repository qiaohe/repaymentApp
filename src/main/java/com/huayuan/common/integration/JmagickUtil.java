package com.huayuan.common.integration;

import magick.ImageInfo;
import magick.MagickImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard Xue on 14-7-13.
 * Jmagick 不支持64bit Windows
 */
public class JmagickUtil {

    private static Logger log = LoggerFactory.getLogger("JmagickUtil");

    /**
     * 缩放图片--重置图片的大小
     *
     * @param srcPackage  源图片文件夹
     * @param srcFileName 源图片名称
     * @param toPackage   目的图片文件夹
     * @param toFileName  目的图片名称
     * @param w           目的图片宽度
     * @param h           目的图片高度
     */
    public static void resize(String srcPackage, String srcFileName, String toPackage, String toFileName, int w, int h) {
        try {
            System.setProperty("jmagick.systemclassloader", "no");
            ImageInfo info = new ImageInfo(srcPackage + srcFileName);
            MagickImage image = new MagickImage(info);
            MagickImage scaled = image.scaleImage(w, h);
            scaled.setFileName(toPackage + toFileName);
            scaled.writeImage(info);
        } catch (Exception ex) {
            log.error("JmagickUtil method resize:",ex);
        }
    }

    /**
     * 裁剪图片
     *
     * @param srcPackage  源图片文件夹
     * @param srcFileName 源图片名称
     * @param toPackage   目的文件件
     * @param toFileName  目的文件名
     * @param x           图片左上角的x坐标
     * @param y           图片左上角的y坐标
     * @param w           截取图片的宽度
     * @param h           截取图片的高度
     */
    public static void cropImage(String srcPackage, String srcFileName,String toPackage, String toFileName, int x, int y, int w, int h) {
        MagickImage cropImage = null;
        try {
            String srcPath = srcPackage + srcFileName;
            ImageInfo infoS = new ImageInfo(srcPath);
            MagickImage image = new MagickImage(infoS);
            Rectangle rect = new Rectangle(x, y, w, h);
            cropImage = image.cropImage(rect);
            String toPath = toPackage + toFileName;
            cropImage.setFileName(toPath);
            cropImage.writeImage(infoS);
        } catch (Exception e) {
            log.error("JmagickUtil method cropImage:",e);
        } finally {
            if (cropImage != null) {
                cropImage.destroyImages();
            }
        }
    }

    /**
     * 旋转图片
     *
     * @param srcPackage  源图片文件夹
     * @param srcFileName 源图片名称
     * @param toPackage   目的文件件
     * @param toFileName  目的文件名
     * @param rotate      图片旋转的角度
     */
    public static void rotateImage(String srcPackage, String srcFileName,String toPackage, String toFileName, double rotate) {
        MagickImage rotateImage = null;
        try {
            String srcPath = srcPackage + srcFileName;
            ImageInfo infoS = new ImageInfo(srcPath);
            MagickImage image = new MagickImage(infoS);
            rotateImage = image.rotateImage(10);
            String toPath = toPackage + toFileName;
            rotateImage.setFileName(toPath);
            rotateImage.writeImage(infoS);
        } catch (Exception e) {
            log.error("JmagickUtil method rotateImage:",e);
        } finally {
            if (rotateImage != null) {
                rotateImage.destroyImages();
            }
        }
    }

    /**
     * 裁剪图片
     *
     * @param srcPackage  源图片文件夹
     * @param srcFileName 源图片名称
     * @param toPackage   目的文件件
     * @param toFileName  目的文件名
     * @param x           图片左上角的x坐标
     * @param y           图片左上角的y坐标
     * @param w           截取图片的宽度
     * @param h           截取图片的高度
     */
    public static void chopImage(String srcPackage, String srcFileName, String toPackage, String toFileName, int x, int y, int w, int h) {
        MagickImage chopImage = null;
        try {
            String srcPath = srcPackage + srcFileName;
            ImageInfo infoS = new ImageInfo(srcPath);
            MagickImage image = new MagickImage(infoS);
            Rectangle rect = new Rectangle(x, y, w, h);
            chopImage = image.cropImage(rect);
            String toPath = toPackage + toFileName;
            chopImage.setFileName(toPath);
            chopImage.writeImage(infoS);
        } catch (Exception e) {
            log.error("JmagickUtil method chopImage:",e);
        } finally {
            if (chopImage != null) {
                chopImage.destroyImages();
            }
        }
    }

    /**
     * 合并图片
     *
     * @param srcPathList 源图片路径名称列表
     * @param toPathFile  合并后图片路径名称
     * @param orientation 合并方位：横向(+)、纵向(-)
     * @return
     */
    public static void convert(List srcPathList, String toPathFile, String orientation) {
        /*  合并图片命令格式:
         * convert +append 1.jpg 2.jpg 3.jpg ... 0.jpg //横向合并（最后一个为合并成功的文件名称）
         * convert -append 1.jpg 2.jpg 3.jpg ... 0.jpg  //纵向合并（最后一个为合并成功的文件名称）
        */
        StringBuffer command = new StringBuffer("");
        command.append("convert ");
        command.append(orientation + "append ");
        int length = srcPathList.size();
        for (int i = 0; i < length; i++) {
            command.append(srcPathList.get(i) + " ");
        }
        command.append(toPathFile);
        String[] str = {command.toString()};
        JmagickUtil.exec(str);
    }

    /**
     * 调用windows的命令行并执行命令
     *
     * @param command 命令行窗口中要执行的命令字符串
     */
    public static void exec(String[] command) {
        Process proc = null;
        InputStream is = null;
        String comman = command[0];
        String[] cmd = {"cmd.exe", "/c", comman};
        try {
            proc = Runtime.getRuntime().exec(cmd);
            int result = proc.waitFor();
            System.out.println("Process result:" + result);
            is = proc.getInputStream();
            byte[] b = new byte[1024];
            is.read(b);
            is.close();
        } catch (Exception e) {
            log.error("JmagickUtil method exec:",e);
        }
    }

    public static void main(String[] args) {
        JmagickUtil obj = new JmagickUtil();
        try {
            /*String srcPackage = "D:/test/fileSource/";
            String srcFileName = "004.jpg";
            String toPackage = "D:/test/desk/";
            String toFileName = "004.jpg";*/
            //File in =new File(srcPackage+srcFileName);
            //File out = new File(toPackage+toFileName);
            //obj.cropImage(srcPackage, srcFileName, toPackage, toFileName, 200, 200, 300, 200); //截图
            //obj.chopImage(srcPackage, srcFileName, toPackage, toFileName, 200, 200, 300, 200); //截图
            //obj.resize(srcPackage, srcFileName, toPackage, toFileName,300, 200); //缩放

            List srcFileList1 = new ArrayList();
            srcFileList1.add("D:/test/desk/003.jpg");
            srcFileList1.add("D:/test/desk/004.jpg");
            String toPath1 = "D:/test/desk/_003_004.jpg";
            obj.convert(srcFileList1, toPath1, "+");

            List srcFileList2 = new ArrayList();
            srcFileList2.add("D:/test/desk/001.jpg");
            srcFileList2.add("D:/test/desk/002.jpg");
            String toPath2 = "D:/test/desk/_001_002.jpg";
            obj.convert(srcFileList2, toPath2, "+");

            List srcFileList3 = new ArrayList();
            srcFileList3.add(toPath1);
            srcFileList3.add(toPath2);
            String toPath3 = "D:/test/desk/_001_002_003_004.jpg";
            obj.convert(srcFileList3, toPath3, "-");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
