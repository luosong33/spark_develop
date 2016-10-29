/**
 * File: Mylock.java
 * General 
 * @author luosong
 * version 1.0 2016年4月6日: 下午7:54:19
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mylock {

	static Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		
		new Thread("thread1"){
			public void run(){
				if(lock.tryLock()){
					try {
						System.out.println(Thread.currentThread().getName()+"====获得锁====");
					} catch (Exception e) {
	//					e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		}.start();
		
		new Thread("thread2"){				/* tryLock()判断是否获得锁   */
			public void run(){
				if(lock.tryLock()){
					try {
						System.out.println(Thread.currentThread().getName()+"====获得锁====");
					} catch (Exception e) {
	//					e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		}.start();
		
	}
	
}
