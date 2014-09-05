package com.huayuan.common.integration;

import com.huayuan.common.App;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

import java.io.IOException;

/**
 * Created by Richard Xue on 14-7-15.
 */
public final class ImageTools {
    private static final ConvertCmd convertCmd = new ConvertCmd();

    /** ImageMagick的路径 */
    public static String imageMagickPath = null;
    /** 是否为Windows系统 */
    private static boolean isWin = false;

    static {
        /**
         * 获取ImageMagick的路径,linux下不要设置此值
         */
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.indexOf("win") > -1) {
            isWin = true;
            imageMagickPath = App.getInstance().getImageMagickPath();
        }
    }

    /**
     * 根据坐标裁剪图片
     *
     * @param srcPath 要裁剪图片的路径
     * @param newPath 裁剪图片后的路径
     * @param x       起始横坐标
     * @param y       起始挫坐标
     * @param x1      结束横坐标
     * @param y1      结束挫坐标
     */
    public static void cropImage(String srcPath, String newPath, int x, int y, int x1,int y1) throws InterruptedException, IOException, IM4JavaException {
        int width = x1 - x;
        int height = y1 - y;
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        /**
         * width：裁剪的宽度
         * height：裁剪的高度
         * x：裁剪的横坐标
         * y：裁剪的挫坐标
         */
        op.crop(width, height, x, y);
        op.addImage(newPath);
        if(isWin) {
            convertCmd.setSearchPath(imageMagickPath);
        }
        convertCmd.run(op);
    }

    /**
     * 根据尺寸缩放图片
     *
     * @param width   缩放后的图片宽度
     * @param height  缩放后的图片高度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     */
    public static void resizeImage(String srcPath, String newPath, int width, int height) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, height);
        op.addImage(newPath);
        //linux下不要设置此值，不然会报错
        if(isWin) {
            convertCmd.setSearchPath(imageMagickPath);
        }
        convertCmd.run(op);
    }

    /**
     * 根据尺寸缩放图片
     *
     * @param angle   旋转过图片的角度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     */
    public static void rotateImage(String srcPath, String newPath, Double angle) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.rotate(angle);
        op.addImage(newPath);
        //linux下不要设置此值，不然会报错
        if(isWin) {
            convertCmd.setSearchPath(imageMagickPath);
        }
        convertCmd.run(op);
    }

    /**
     * 根据宽度缩放图片
     *
     * @param width   缩放后的图片宽度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     */
    public static void cutImage(String srcPath, String newPath, int width) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, null);
        op.addImage(newPath);
        //linux下不要设置此值，不然会报错
        if(isWin) {
            convertCmd.setSearchPath(imageMagickPath);
        }
        convertCmd.run(op);
    }

    /**
     * 给图片加水印
     *
     * @param srcPath 源图片路径
     */
    public static void addImgText(String srcPath) throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        op.font("宋体").gravity("southeast").pointsize(18).fill("#BCBFC8").draw("text 5,5 juziku.com");
        op.addImage();
        op.addImage();
        //linux下不要设置此值，不然会报错
        if(isWin) {
            convertCmd.setSearchPath(imageMagickPath);
        }
        convertCmd.run(op, srcPath, srcPath);
    }

}
