/**
 * File: RunnableTest1.java
 * General 
 * @author luosong
 * version 1.0 2016年1月12日: 下午9:51:21
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.thread;

public class RunnableTest1 implements Runnable {

	private static int countdown = 12; // default
	
	public RunnableTest1(){}
	public RunnableTest1(int countdown){
		this.countdown = countdown;
	}
	
	@Override
	public void run() {
		while(countdown-- > 0){
			System.out.println(status());
			Thread.yield(); // 是对线程调度器的一种建议，表示已执行完重要部分，可以切换资源给其他任务执行一段时间
		}
	}
	
	public static String status(){
		return "$"+"("
//				+ (countdown > 0 ? countdown : "launch")
//				+ ")";
				+ (countdown == 11 ? "countdown" 
						: countdown > 0 ? countdown
						: "launch")
				+ ")";
	}
	
	public static void main(String[] args) {
		
		// test1，定义任务runnable，普通方法调用方式
//		RunnableTest1 test1 = new RunnableTest1(); 
//		test1.run();
		
		// test2，将任务附着到线程上
		Thread t = new Thread(new RunnableTest1());
		t.start();
		System.out.println("不同线程输出"); // 由main线程执行，与RunnableTest1.run()同时执行
		
		
	}
}
