/**
 * File: Snippet.java
 * General 
 * @author luosong
 * version 1.0 2016年3月8日: 下午5:19:50
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class GUIDGenerator {
	
	  private static final int BYTE_FF = 255;
	  private static final int BYTE_10 = 16;
	  private String valueBeforeMD5;
	  private String valueAfterMD5;
	  private static Random myRand;
	  private static SecureRandom mySecureRand = new SecureRandom();

	  private GUIDGenerator()
	  {
	    getRandomGUID(false);
	  }

	  private void getRandomGUID(boolean secure)
	  {
	    MessageDigest md5 = null;
	    StringBuffer sbValueBeforeMD5 = new StringBuffer();
	    try
	    {
	      md5 = MessageDigest.getInstance("MD5");
	    }
	    catch (NoSuchAlgorithmException e) {
	      throw new RuntimeException(e);
	    }
	    try
	    {
	      String id = System.getProperty("user.name");

	      long time = System.currentTimeMillis();
	      long rand = 0L;

	      if (secure)
	        rand = mySecureRand.nextLong();
	      else {
	        rand = myRand.nextLong();
	      }

	      sbValueBeforeMD5.append(id);
	      sbValueBeforeMD5.append(":");
	      sbValueBeforeMD5.append(Long.toString(time));
	      sbValueBeforeMD5.append(":");
	      sbValueBeforeMD5.append(Long.toString(rand));

	      this.valueBeforeMD5 = sbValueBeforeMD5.toString();
	      md5.update(this.valueBeforeMD5.getBytes());

	      byte[] array = md5.digest();
	      StringBuffer sb = new StringBuffer();

	      for (int j = 0; j < array.length; j++) {
	        int b = array[j] & 0xFF;

	        if (b < 16) {
	          sb.append('0');
	        }
	        sb.append(Integer.toHexString(b));
	      }

	      this.valueAfterMD5 = sb.toString();
	    }
	    catch (Exception e) {
	    }
	  }

	  public String toString()
	  {
	    String raw = this.valueAfterMD5.toUpperCase();

	    return raw;
	  }

	  public static String generateGUID()
	  {
	    return new GUIDGenerator().toString();
	  }

	  static
	  {
	    long secureInitializer = mySecureRand.nextLong();

	    myRand = new Random(secureInitializer);
	  }
}

