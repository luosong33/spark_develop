/**
 * File: Mysyschronized.java
 * General 
 * @author luosong
 * version 1.0 2016年4月6日: 下午6:49:55
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.thread;

public class MySynchronized {


	private static final MySynchronized thread1 = new MySynchronized();
	private static final MySynchronized thread2 = new MySynchronized();
	
	/**
	 * synchronized 会对资源锁定，没有释放前占用资源
	 */
	public static void main(String[] args) {

		new Thread("thread1"){
			public void run(){
				synchronized (thread1){
					System.out.println("thread1");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		new Thread("thread2"){
			public void run(){
				synchronized (thread1){
					System.out.println("thread2");
				}
			}
		}.start();
		
	}

}
