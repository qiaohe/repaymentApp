package com.huayuan.common.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
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
