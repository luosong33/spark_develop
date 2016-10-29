/**
 * File: StaticIniBlockOrderTest.java
 * General 
 * @author luosong
 * version 1.0 2016年1月12日: 下午4:49:10
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.test;

class Parent{ 
    static String name = "hello"; 
    static { 
        System.out.println("parent static block"); 
    }
    {
        System.out.println("parent block"); 
    }
    public Parent(){ 
        System.out.println("parent constructor"); 
    } 
} 
  
class Child extends Parent{ 
    static String childName = "hello"; 
    static { 
        System.out.println("child static block"); 
    }
    {
        System.out.println("child block"); 
    } 
    public Child(){ 
        System.out.println("child constructor"); 
    } 
} 
  
public class StaticIniBlockOrderTest { 
  
    public static void main(String[] args) { 
        new Child();//语句(*) 

    } 
}
