/**
 * File: ResultProcessor.java
 * General 
 * @author luosong
 * version 1.0 2016年3月14日: 下午6:00:39
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.dispatch;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class ResultProcessor implements Runnable{

	private static final Queue<Object> resultQueue = new ConcurrentLinkedQueue<>();
	
	private ResultProcessor(){
		Executors.newSingleThreadExecutor().execute(this);
	}
	private static final ResultProcessor inst = new ResultProcessor();
	public static ResultProcessor getInstance(){
		return inst;
	}
	
	public void run(){
		
	}
}


