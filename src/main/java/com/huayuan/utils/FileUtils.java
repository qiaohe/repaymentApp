package com.huayuan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {
	
	/**
	 * 读取text文件的内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		File file = new File(fileName);
		String readStr = "";
		if (file.isFile() && file.exists()) {
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineText = null;
				while ((lineText = bufferedReader.readLine()) != null) {
					readStr += lineText;
				}
				read.close();
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("找不到指定文件...");
		}
		return readStr;
	}
	
	public static void delFile(String fileName){
		if(fileName == null || fileName == ""){
			throw new RuntimeException("文件路径为空！");
		}
		File file = new File(fileName);
		if(file != null && file.exists() && file.isFile()){
			file.delete();
		}
	}
}
