/**
 * File: QuickStart.java
 * General 
 * @author luosong
 * version 1.0 2016年1月2日: 下午12:21:10
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuickStart {

	public static void main(String[] args) {
//		One one = new One();
//		one.test();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		One one = (One) ctx.getBean("one");
		one.test();
	}
	
}

