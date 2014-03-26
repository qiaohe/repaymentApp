package com.huayuan.common.imageprocessing;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dell on 14-3-25.
 */
public class ImageMagicWrapper {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImageMagicWrapper.class);

    private static final String IMAGE_MAGIC_PATH = "";
    private static final String RESIZE_COMMAND_PATTERN = "%s -resize %dx%d! %s %s";

    private String getResizeCommand(String sourceFileName, String targetFileName, int width, int height) {
        return String.format(RESIZE_COMMAND_PATTERN, IMAGE_MAGIC_PATH, sourceFileName, targetFileName, width, height);
    }

    public String getGrayCommand(String sourceFileName, String targetFileName) {
        return IMAGE_MAGIC_PATH + " " + sourceFileName + " -set colorspace RGB -colorspace gray " + targetFileName;
    }


    public String getQualityCommand(String sourceFileName, String targetFileName) {

        return IMAGE_MAGIC_PATH + " " + sourceFileName
                + " -colorspace RGB +sigmoidal-contrast 11.6933 -define "
                + "filter:filter=Sinc -define filter:window=Jinc -define "
                + "filter:lobes=3 -resize 600% -sigmoidal-contrast 11.6933 "
                + "-colorspace sRGB " + targetFileName;
    }

    private String getTargetFileName() {
        return null;
    }

    public String resize(final String sourceFileName) {
        String cmd = getResizeCommand(sourceFileName, getTargetFileName(), 700, 340);
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(cmd);
        return null;
    }

    public String cut(String sourceFileName, int x, int y, int x1, int y1) {
        int width = x1 - x;
        int height = y1 - y;
        IMOperation op = new IMOperation();
        op.addImage(sourceFileName);
        op.crop(width, height, x, y);
        op.addImage("");
        ConvertCmd convert = new ConvertCmd();
        convert.setSearchPath(IMAGE_MAGIC_PATH);
        try {
            convert.run(op);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException | IM4JavaException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
