/**
 * File: Random.java
 * General 
 * @author luosong
 * version 1.0 2015年12月30日: 下午7:16:46
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.test;

import java.util.Random;

public class RandomTest {
	
	public static void main(String[] args) {

		/*int [] i = new int[10001];
		for (int j = 0;j<=10000;j++){
			i[j] = j;
		}*/
		
		/**
		 * 创造思想
		 * 1、随机输出n个数，即循环n
		 * 2、随机输出不重复n个数，即循环n中，循环比对当前随机数与随机容器
		 * 
		 * 实现思想
		 * 1、拿到一个随机数
		 * 2、与容器比对，相同跳出比对循环，并给相同标记
		 * 3、根据标记存入容器
		 */
		int[] intRet = new int[10]; // 所有随机数容器
		int intRd = 0; // 存放随机数
        int count = 0; // 记录生成的随机数个数
        int flag = 0; // 是否已经生成过标志
        while(count<10){
             Random rdm = new Random(System.currentTimeMillis()); // System.currentTimeMillis()获得当前毫秒时间
             intRd = Math.abs(rdm.nextInt())%10; // 取模即可指定随机数范围
             for(int i=0;i<count;i++){
                 if(intRet[i]==intRd){
                     flag = 1;
                     break;
                 }else{
                     flag = 0;
                 }
             }
             if(flag==0){ // 取到之后以计数为角标
                 intRet[count] = intRd;
                 count++;
             }
	    }
        
        for(int t=0;t<10;t++){
	       System.out.println("->"+intRet[t]);
        }
	   
	   
	}
}
