package com.huayuan.domain.recognizer;

import com.huayuan.domain.IdCard;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by dell on 14-3-21.
 */
public class IdCardRecognizer implements Recognizer {
    @Override
    public Object recognize(String fileName) {
        ReadCard readCard = new ReadCard();
        File file = new File("");
        String postfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String resizeFile = file.getAbsoluteFile() + "/temp" + postfix;
        //set image size 700 * 340
        String reCmd = readCard.resize(fileName,resizeFile,700,340);
        readCard.runCmd(reCmd);

        //姓名
		String name = readCard.readCard(resizeFile, 110, 30, 180, 55);
		//民族
		String nationality = readCard.readCard(resizeFile, 262, 80, 80, 50);
		//地址
		String address = readCard.readCard(resizeFile, 110, 160, 350, 100);
        //身份证号码
        String idCardNumber = readCard.readCard(resizeFile, 210, 250, 500, 100);

        IdCardInfo idCardInfo = new IdCardInfo();
        idCardInfo.setName(name);
        idCardInfo.setNationality(nationality);
        idCardInfo.setAddress(address);
        idCardInfo.setIdCardNumber(idCardNumber);

        return idCardInfo;
    }
}
