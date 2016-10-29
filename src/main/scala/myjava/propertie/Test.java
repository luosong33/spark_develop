/**
 * File: Test.java
 * General 
 * @author luosong
 * version 1.0 2016年5月5日: 下午11:19:18
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.propertie;

import java.io.FileInputStream;
import java.util.Properties;

public class Test {

	
	public static void main(String[] args) throws Exception {
		Properties prop = new Properties();//属性集合对象      
		FileInputStream fis = new FileInputStream("Baofoo.properties");//属性文件流      
		prop.load(fis);//将属性文件流装载到Properties对象中
		//获取属性值，sitename已在文件中定义      
		System.out.println("获取属性值：sitename=" + prop.getProperty("sitename"));      
		//获取属性值，country未在文件中定义，将在此程序中返回一个默认值，但并不修改属性文件      
		System.out.println("获取属性值：country=" + prop.getProperty("country", "中国"));
	}
}
