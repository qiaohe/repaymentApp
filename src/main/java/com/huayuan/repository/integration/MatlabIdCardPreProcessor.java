package com.huayuan.repository.integration;

import com.mathworks.toolbox.javabuilder.MWCharArray;
import com.mathworks.toolbox.javabuilder.MWException;
import idcardprocessor.IdCardPreProcessor;

/**
 * Created by dell on 14-6-2.
 */
public class MatlabIdCardPreProcessor {
    private static final IdCardPreProcessor ID_CARD_PRE_PROCESSOR;

    static {
        try {
            ID_CARD_PRE_PROCESSOR = new IdCardPreProcessor();
        } catch (MWException e) {
            throw new IllegalStateException("Can not init MatLab runtime");
        }
    }

    public boolean batchPreProcessingImage(String sourceDir, String destinationDir) {
        MWCharArray source = new MWCharArray(sourceDir);
        MWCharArray destination = new MWCharArray(destinationDir);
        try {
            Object[] result = ID_CARD_PRE_PROCESSOR.batchPreProcessingImage(1, source, destination);
            return result != null && result.length > 0;
        } catch (MWException e) {
            throw new IllegalStateException("Can not init MatLab function batchPreProcessingImage");
        }
    }

    public boolean preProcessingImage(String fileName, String destinationDir) {
        MWCharArray fName = new MWCharArray(fileName);
        MWCharArray destination = new MWCharArray(destinationDir);
        try {
            Object[] result = ID_CARD_PRE_PROCESSOR.preProcessingImage(1, fName, destination);
            return result != null && result.length > 0;
        } catch (MWException e) {
            throw new IllegalStateException("Can not init MatLab function preProcessingImage");
        }
    }
}
