package com.huayuan.common.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Richard Xue on 14-6-18.
 */
public final class OperUtil {

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

    public static void packageToZip(File[] srcFiles,String destPath,String zipName) {
        File tempFile = new File(destPath);
        if(!tempFile.exists() || !tempFile.isDirectory()) {
            tempFile.mkdirs();
        }
        FileOutputStream dest = null;
        ZipOutputStream out = null;
        BufferedInputStream origin = null;
        try {
            destPath = destPath +"/"+ zipName;
            dest = new FileOutputStream(destPath);
            out = new ZipOutputStream(new BufferedOutputStream(dest));

            for(File file : srcFiles) {
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi);
                ZipEntry entry = new ZipEntry(file.getName());
                out.putNextEntry(entry);

                int count = 0;
                byte data[] = new byte[2048];
                while ((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                    out.flush();
                }
                origin.close();
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFileOrDirectory(String path) {
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在,不存在返回 false
        if (!file.exists()) {
            return flag;
        } else {
            if (file.isFile()) {
                return deleteFile(path);
            } else {
                return deleteDirectory(path);
            }
        }
    }

    public static boolean deleteFile(String path) {
        boolean flag = false;
        File file = new File(path);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean deleteDirectory (String path) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

}
