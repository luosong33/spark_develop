/**
 * File: MyLockExtends.java
 * General 
 * @author luosong
 * version 1.0 2016年4月6日: 下午8:21:40
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockExtends implements Runnable{

	public static String flag;
	MyLockExtends(String flag){
		this.flag = flag;
	}
	
	static Lock lock = new ReentrantLock();
	
	@Override
	public void run() {
		if("B".equals(flag)){
			lock.lock();
		}else{
			lock.unlock();
		}
		if(lock.tryLock()){
			try {
				System.out.println(Thread.currentThread().getName()+flag+"======获得锁=====");
			} catch (Exception e) {
			} finally{
				lock.unlock();
				System.out.println(Thread.currentThread().getName()+flag+"======释放锁=====");
			}
		}
	}
	
	
	public static void main(String[] args) {
//		Thread thread1 = new Thread(new MyLockExtends("A"));
//		Thread thread2 = new Thread(new MyLockExtends("B"));
//		thread1.start();
//		thread2.start();
		for(int i = 0;i < 100;i++){
//			Thread thread_a = new Thread(new MyLockExtends("A"));
			Thread thread_b = new Thread(new MyLockExtends("B"));
//			thread_a.start();
			thread_b.start();
			thread_b.stop();
		}
		
	}

	
}
