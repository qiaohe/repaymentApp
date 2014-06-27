package com.huayuan.repository.integration;

import com.mathworks.toolbox.javabuilder.MWCharArray;
import com.mathworks.toolbox.javabuilder.MWException;
import processor.ImageProcessor;

/**
 * Created by Administrator on 14-6-27.
 */
public class MatlabIdCardPreProcessor {
    private static final ImageProcessor processor;

    static {
        try {
            processor = new ImageProcessor();
        } catch (MWException e) {
            throw new IllegalStateException("Can not init MatLab runtime");
        }
    }

    public static boolean batchPreProcessingImage(String sourceDir, String destinationDir) {
        MWCharArray source = new MWCharArray(sourceDir);
        MWCharArray destination = new MWCharArray(destinationDir);
        try {
            Object[] result = processor.batchPreProcessingImage(1, source, destination);
            return result != null && result.length > 0;
        } catch (MWException e) {
            throw new IllegalStateException("Can not init MatLab function batchPreProcessingImage");
        }
    }

    public static boolean preProcessingImage(String fileName, String destinationDir) {
        MWCharArray fName = new MWCharArray(fileName);
        MWCharArray destination = new MWCharArray(destinationDir);
        try {
            Object[] result = processor.preProcessingImage(1, fName, destination);
            return result != null && result.length > 0;
        } catch (MWException e) {
            throw new IllegalStateException("Can not init MatLab function preProcessingImage");
        }
    }

}
