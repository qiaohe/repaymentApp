package com.huayuan.common.integration;

import org.junit.Test;

/**
 * Created by Administrator on 14-7-15.
 */
public class TestImageTools {

    @Test
    public void testCropImage() {
        try {
            ImageTools.cropImage("F:/test.jpg", "F:/res1.jpg", 98, 48, 370, 320);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResizeImage() {
        try {
            ImageTools.resizeImage("F:/test.jpg", "F:/res2.jpg", 370, 320);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRotateImage() {
        try {
            ImageTools.rotateImage("F:/test.jpg", "F:/res3.jpg", 30.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
