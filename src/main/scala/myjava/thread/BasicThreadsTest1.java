/**
 * File: BasicThreadsTest1.java
 * General 
 * @author luosong
 * version 1.0 2016年1月17日: 上午11:00:50
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.thread;

public class BasicThreadsTest1 {
	
	public static void main(String[] args) {
		Thread t = new Thread(new RunnableTest1());
		t.start();
		System.out.println("不同线程输出");
	}
}
