/**
 * File: Singleton.java
 * General 
 * @author luosong
 * version 1.0 2016年2月29日: 下午3:49:23
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.pattern;

public class Singleton {

	// 饿汉式，在类加载的时候就创建一个单例模式.
	static class Singleton2 {  
	      
	    // 1.私有的构造函数,确保不能在类的外部访问该类的构造函数  
	    private Singleton2(){  
	        System.out.println("构造函数执行了");  
	    }  
	      
	    // 2.私有的唯一的静态实例变量,在类加载的时候就创建好单例对象  
	    private final static Singleton2 instance = new Singleton2();  
	      
	    // 3.公开的静态工厂返回此类的唯一实例  
	    public static Singleton2 getInstance(){  
	        return instance;  
	    }  
	}  
	  
	// 测试类  
	static class Test2{  
	    public static void main(String[] args) {  
	        Singleton2 s = Singleton2.getInstance();  
	        System.out.println(s);  
	        Singleton2 s2 = Singleton2.getInstance();  
	        System.out.println(s2);  
	        System.out.println(Singleton2.getInstance());  
	        System.out.println(Singleton2.getInstance());  
	    }  
	}  
}
