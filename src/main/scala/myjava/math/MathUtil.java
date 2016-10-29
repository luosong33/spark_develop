/**
 * File: MathUtil.java
 * General 
 * @author luosong
 * version 1.0 2016年6月22日: 下午12:13:40
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.math;

import java.io.*;
import java.util.Random;

public class MathUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 /*double random = Math.random();
		 System.out.println(random);*/
		
		String urlStr = "";
		for (int i = 0; i < 1000; i++) {

			Random rand = new Random();
			int randNum = rand.nextInt(100);
			urlStr = "name" + randNum + " age" + randNum + " url" + randNum + "\n" + urlStr;
		}
		System.out.println(urlStr);

		String path = "D:/test/url_topN.txt";
		createFile(path);
		try {
			writeByFileWrite(path,urlStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createFile(String path) {

		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	// 1.向文件写入内容
	// writeByFileWrite(sDestFile, sContent);

	// 2.FileOutputStream向文件写入内容
	// writeByFileWrite(sDestFile, sContent);

	// 2.OutputStreamWriter向文件写入内容
	// writeByOutputStreamWrite(sDestFile, sContent);

	/**
	 * 用FileWrite向文件写入内容
	 * 
	 * @param _destFile
	 * @throws IOException
	 */
	public static void writeByFileWrite(String _sDestFile, String _sContent)

			throws IOException {
		FileWriter fw = null;
		try {
			fw = new FileWriter(_sDestFile);
			fw.write(_sContent);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fw != null) {
				fw.close();
				fw = null;
			}
		}
	}

	/**
	 * 用FileOutputStream向文件写入内容
	 * 
	 * @param _destFile
	 * @throws IOException
	 */

	public static void writeByFileOutputStream(String _sDestFile,

			String _sContent) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_sDestFile);
			fos.write(_sContent.getBytes());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
				fos = null;
			}
		}
	}

	/**
	 * 用OutputStreamWrite向文件写入内容
	 * 
	 * @param _destFile
	 * @throws IOException
	 */
	public static void writeByOutputStreamWrite(String _sDestFile, String _sContent) throws IOException {
		OutputStreamWriter os = null;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(_sDestFile);
			os = new OutputStreamWriter(fos, "UTF-8");
			os.write(_sContent);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
				os = null;
			}
			if (fos != null) {
				fos.close();
				fos = null;
			}
		}
	}

}
