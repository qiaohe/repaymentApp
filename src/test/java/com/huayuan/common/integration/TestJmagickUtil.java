package com.huayuan.common.integration;

import org.junit.Test;

/**
 * Created by Richard Xue on 14-7-15.
 */
public class TestJmagickUtil {

    @Test
    public void testResize() {
        String srcPackage = "F:/";
        String srcFileName = "test.jpg";
        String toPackage = "F:/test/";
        String toFileName = "004.jpg";
        JmagickUtil.resize(srcPackage, srcFileName, toPackage, toFileName,300, 200); //缩放
    }

}
