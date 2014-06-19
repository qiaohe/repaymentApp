package com.huayuan.common.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Richard Xue on 14-6-18.
 */
public final class ImageUtil {

    public static void cropImage(String srcPath,String destPath,int x,int y,int width,int height) {
        try {
            BufferedImage original = ImageIO.read(new File(srcPath));
            ImageFilter cropFilter = new CropImageFilter(x,y,width,height);

            Image image = original.getScaledInstance(original.getWidth(),original.getHeight(),Image.SCALE_DEFAULT);
            Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));

            BufferedImage tag = new BufferedImage(x, y,BufferedImage.TYPE_INT_RGB); // error ?
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(img, 0, 0, null);
            g.dispose();
            // 输出为文件
            ImageIO.write(tag, "JPEG", new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cropImage2(String srcPath,String destPath,int x,int y,int width,int height) {
        FileInputStream inputStream = null;
        ImageInputStream imageInputStream = null;
        try {
            inputStream = new FileInputStream(srcPath);
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = it.next();
            // 获取图片流
            imageInputStream = ImageIO.createImageInputStream(inputStream);
            // 读取源
            reader.setInput(imageInputStream,true);
            // 指定从JavaImageI/O框架的上下文中的流转换一幅图像或一组图像
            ImageReadParam param = reader.getDefaultReadParam();
            // 图片裁剪区域
            Rectangle rect = new Rectangle(x, y, width, height);
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            // 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将它作为一个完整的 BufferedImage 返回。
            BufferedImage bi = reader.read(0,param);
            //保存新图片
            ImageIO.write(bi, "jpg", new File(destPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
