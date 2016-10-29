/**
 * File: Encryp.java
 * General 
 * @author luosong
 * version 1.0 2015年12月24日: 上午9:40:37
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.encryp;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Encryp {
	/**
	 * Base64加密
	 */
	public static String encodeBase64(String cookieStr){  
      
		try {  
            cookieStr = new String(Base64.encodeBase64(cookieStr.getBytes("UTF-8")));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return cookieStr;  
	}
	
	/**
	 * Base64解密
	 */
	public static String decodeBase64(String cookieStr){  
        try {  
            cookieStr = new String(Base64.decodeBase64(cookieStr.getBytes()), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return cookieStr;  
    }
}
