package com.huayuan.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger log = LoggerFactory.getLogger("OperUtil");

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
            log.error("OperUtil cropImage :",e);
        } catch (IOException e) {
            log.error("OperUtil cropImage :",e);
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

    /**
     * 复制文件
     * @param sourcePath  源文件路径
     * @param targetPath  目标文件路径
     * @throws IOException
     */
    public static void copyFile(String sourcePath, String targetPath) throws IOException {
        copyFile(new File(sourcePath),new File(targetPath));
    }

    /**
     * 复制文件
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 复制文件夹
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

}
