/**
 * File: Mian.java
 * General 
 * @author luosong
 * version 1.0 2015年12月3日: 上午11:25:40
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.dispatch;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class Main implements Callable<String>{
	private AtomicInteger count = new AtomicInteger(0);
	
	public static void main(String[] args) {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
//		pool.scheduleAtFixedRate(new Main(), 0, 1, TimeUnit.SECONDS);
//		pool.scheduleAtFixedRate(new Main(), 0, 1, TimeUnit.SECONDS);
		try {
			Object object = pool.submit(new Main()).get();
			System.out.println(object);
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		pool.shutdownNow();
		System.out.println("End");
	}


	@Override
	public String call() throws Exception {
		Thread.sleep(2000);
		return "aaa";
	}
}
