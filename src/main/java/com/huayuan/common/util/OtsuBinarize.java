package com.huayuan.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by dell on 14-5-16.
 */
public class OtsuBinarize {

    public static int[] imageHistogram(BufferedImage input) {
        int[] histogram = new int[256];
        for (int i = 0; i < histogram.length; i++) histogram[i] = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                int red = new Color(input.getRGB(i, j)).getRed();
                histogram[red]++;
            }
        }
        return histogram;
    }

    public static BufferedImage toGray(BufferedImage original) {
        int alpha, red, green, blue;
        int newPixel;
        BufferedImage lum = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();
                red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
                newPixel = colorToRGB(alpha, red, red, red);
                lum.setRGB(i, j, newPixel);
            }
        }
        return lum;
    }

    public static BufferedImage toGrayPicture(BufferedImage originalPic) {
        int imageWidth = originalPic.getWidth();
        int imageHeight = originalPic.getHeight();
        BufferedImage newPic = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_3BYTE_BGR);
        ColorConvertOp cco = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        cco.filter(originalPic, newPic);
        return newPic;
    }

    public static BufferedImage toGrayPicture(BufferedImage srcImage,RenderingHints hints)  {
        BufferedImage dstImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), srcImage.getType());
        if (hints == null) {
            Graphics2D g2 = dstImage.createGraphics();
            hints = g2.getRenderingHints();
            g2.dispose();
            g2 = null;
        }
        ColorSpace grayCS = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp colorConvertOp = new ColorConvertOp(grayCS, hints);
        colorConvertOp.filter(srcImage, dstImage);
        return dstImage;
    }

    private static int otsuTreshold(BufferedImage original) {
        int[] histogram = imageHistogram(original);
        int total = original.getHeight() * original.getWidth();
        float sum = 0;
        for (int i = 0; i < 256; i++) sum += i * histogram[i];
        float sumB = 0;
        int wB = 0;
        int wF = 0;
        float varMax = 0;
        int threshold = 0;
        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0) continue;
            wF = total - wB;
            if (wF == 0) break;
            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }

    public static byte[] binarize(String imageFileName) throws IOException {
        BufferedImage original = ImageIO.read(new File(imageFileName));
//        BufferedImage grayscale = toGray(original);
//        BufferedImage binarized = binarize(original);
        BufferedImage gray = toGrayPicture(original);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(gray, "jpg", baos);
        byte[] result = baos.toByteArray();
        baos.flush();
        baos.close();
        return result;
    }

    public static BufferedImage binarize(BufferedImage original) {
        int red;
        int newPixel;
        int threshold = 100;// otsuTreshold(original);
        BufferedImage binarized = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                red = new Color(original.getRGB(i, j)).getRed();
                int alpha = new Color(original.getRGB(i, j)).getAlpha();
                if (red > threshold) {
                    newPixel = 255;
                } else {
                    newPixel = 0;
                }
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setRGB(i, j, newPixel);
            }
        }
        return binarized;
    }

    private static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }
}