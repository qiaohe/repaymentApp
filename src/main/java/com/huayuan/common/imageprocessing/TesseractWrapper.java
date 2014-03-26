package com.huayuan.common.imageprocessing;

/**
 * Created by dell on 14-3-25.
 */
public final class TesseractWrapper {
    private static final String TESSERACT_PATH = "";
    private static final String TESSERACT_COMMAND_PATTERN = "%s %s %s -l chi_sim -psd 7 nobatch";

    public String getTesseractCommand(String sourceFileName, String outputFileName) {
        return String.format(TESSERACT_COMMAND_PATTERN, TESSERACT_PATH, sourceFileName, outputFileName);
    }
    public String recognize(String sourceFileName, int x, int y, int x1, int y1) {
       return null;
    }
}
