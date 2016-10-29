/**
 * File: TestFile.java
 * General 
 * @author luosong
 * version 1.0 2016年2月19日: 下午5:19:41
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.file;

import java.io.File;
import java.io.IOException;

public class TestFile {



	public static void main(String[] args) throws Exception {
		String path = "D:/test/url_topN.txt";
		createFile(path);
	}

	public static void createFile(String path) {

		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
