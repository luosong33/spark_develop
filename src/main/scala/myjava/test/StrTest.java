/**
 * File: StrTest.java
 * General 
 * @author luosong
 * version 1.0 2015年12月18日: 下午3:31:13
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.test;

public class StrTest {
	public static void main(String[] args) {
		String str="Hello World";  //待判断的字符串
		String reg=".*ll.*";  //判断字符串中是否含有特定字符串ll
		String reg1="ll";  //判断字符串中是否含有特定字符串ll
		System.out.println(str.matches(reg1));
		System.out.println(str.contains(reg1));
	}
}
