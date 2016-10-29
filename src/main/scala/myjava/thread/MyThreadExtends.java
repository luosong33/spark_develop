/**
 * File: MyThreadExtends.java
 * General 
 * @author luosong
 * version 1.0 2016年4月6日: 下午7:05:43
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.thread;

public class MyThreadExtends extends Thread {

	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("=========="+name+"===========");
	}
	
	public static void main(String[] args) {
		
		MyThreadExtends thread1 = new MyThreadExtends();
		thread1.start(); // 启动进程会创建新的进程(继承方式)
		thread1.run(); // 而调用run只是普通方法调用，打印当前线程
		
		
	}

}
