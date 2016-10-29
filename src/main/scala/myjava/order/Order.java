/**
 * File: PaiXu.java
 * General 
 * @author luosong
 * version 1.0 2015年12月16日: 上午10:07:38
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.order;

public class Order {

	/**
	 * 冒泡排序的精髓是将最大者一个个沉下去或最小者一个个浮上来
	 * i 定义范围，循环到倒数第二个（因为j+1已经到最后一个了），递增，作为j 的内厄条件
	 * j 在范围内依次两两比较，由i 控制比较范围，i 最大，j 范围越小
	 * @param a
	 */
	public static void maoPao(int [] a){
		int temp=0;  
	    for(int i=0;i<a.length-1;i++){  
	        for(int j=0;j<a.length-1-i;j++){  
		    	if(a[j]>a[j+1]){  
		            temp=a[j];  
		            a[j]=a[j+1];  
		            a[j+1]=temp;  
		        }  
	        }  
	    }  
	    for(int i=0;i<a.length;i++){
	        System.out.println(a[i]);
	    }
	}
	
	public static void xuanze(int [] a){
		
	} 
	
	public static void main(String[] args) {
		int a[]={3,6,4,2,11,10,5};
		maoPao(a);
	}
}

