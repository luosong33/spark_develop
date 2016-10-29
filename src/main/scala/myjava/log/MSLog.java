package myjava.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *系统日志输出（基于log4j的再次封装）
 * 
 * @version 3.0
 * @author 北京市宏天信业科技发展有限公司
 * @remark 
 */
public class MSLog
{
 /* private static final Log log    = LogFactory.getLog("PLATFORM");
  private static final Log errlog = LogFactory.getLog("PLATFORMERROR");*/
  static final Logger log = LoggerFactory.getLogger( "PLATFORM" );//.getLogger(MSLog.class);
  static final Logger errlog = LoggerFactory.getLogger( "PLATFORMERROR" );//.getLogger(MSLog.class);
  static final Logger carlog = LoggerFactory.getLogger( "CAR" );//.getLogger(MSLog.class);
  
  /**
   * 输出调试信息
   * @param message  要输出的信息内容
   * @remark 
   */
  public static void debug( String message )
  {
    log.debug( getCaller()+message );
  }
  
  /**
   * 输出调试信息，并且同时抛出异常
   * @param message  要输出的信息内容
   * @param arg1  要抛出的异常
   * @remark 
   */
  public static void debug( String message, Throwable arg1 )
  {
    log.debug( getCaller()+message, arg1 );
  }
  
  /**
   * 输出提示信息
   * @param message  要输出的信息内容
   * @remark 
   */
  public static void info( String message )
  {
    log.info( getCaller()+message );
  }
  
  /**
   * 输出提示信息，并且同时抛出异常
   * @param message  要输出的信息内容
   * @param arg1  要抛出的异常
   * @remark 
   */
  public static void info( String message, Throwable arg1 )
  {
    log.info( getCaller()+message, arg1 );
  }
  
  /**
   * 输出警告信息
   * @param message  要输出的信息内容
   * @remark 
   */
  public static void warn( String message )
  {
    log.warn( getCaller()+message );
  }
  
  /**
   * 输出警告信息，并且同时抛出异常
   * @param message  要输出的信息内容
   * @param arg1  要抛出的异常
   * @remark 
   */
  public static void warn( String message, Throwable arg1 )
  {
    log.warn( getCaller()+message, arg1 );
  }
  
  /**
   * 输出错误信息
   * @param message  要输出的信息内容
   * @remark 错误信息会被输出在日志文件和控制台中
   */
  public static void error( String message )
  {
    errlog.error( getCaller()+message );
    log.error(  getCaller()+message );
    ////system.out.print( getCaller()+message );
  }
  
  /**
   * 输出错误信息，并且同时抛出异常
   * @param message  要输出的信息内容
   * @param arg1  要抛出的异常
   * @remark 错误信息会被输出在日志文件和控制台中
   */
  public static void error( String message, Throwable arg1 )
  {
    errlog.error( getCaller()+message, arg1 );
    log.error(  getCaller()+message, arg1  );
    ////system.out.print( getCaller()+message );
    //arg1.printStackTrace( );
  }
  
  /**
   * 获取调用父类的名称
   * @return 调用父类的名称
   */
  private static String getCaller()
  {
    String strRtn = "";
    StackTraceElement stack[] = (new Throwable()).getStackTrace();
    String className = stack[2].getClassName();
    className = className.substring( className.lastIndexOf( "." )+1 );
    strRtn = "( " + className + ", "+stack[2].getLineNumber();
    ////system.out.println( "strRtn.length:"+strRtn.length() );
    ////system.out.println( "50-strRtn.length:"+(50-strRtn.length()) );
    int tmpLength = strRtn.length();
    for(int i=0;i<35-tmpLength;i++)
    {
      strRtn = strRtn+" ";
    }
    ////system.out.println( "strRtn.length:"+strRtn.length() );
    strRtn = strRtn +" ) ";
    ////system.out.println( "strRtn"+strRtn );
    return strRtn;
    // stack[0]:ms.platform.base.log.MSLog.getCaller
    // stack[1]:ms.platform.base.log.MSLog.info
    // stack[2]:ms.platform.base.interceptor.struts.DebugPrintBeforeStruts.doIntercept
/*    for (i=0; i < stack.length; i++); {
      StackTraceElement ste=stack[i];   
      //system.out.println(ste.getClassName()+"."+ste.getMethodName()+"(...);");   
      //system.out.println(i+"--"+ste.getMethodName());
      //system.out.println(i+"--"+ste.getFileName()); 
      //system.out.println(i+"--"+ste.getLineNumber());
    }  */ 
  }
  /**
   * 输出车辆信息
   * @param message  要输出的信息内容
   * @param arg1  要抛出的异常
   * @remark 
   */
  public static void carInfo( String message)
  {
	  carlog.info( getCaller()+message);
  }
  /**
   * 实体类入参输出
   * @param obj  要输出的实体
   * 
   * @remark 
   */
  public static void outputlog( Object obj)
  {
	  Class<?> cls = obj.getClass();  
      Field fields[] = cls.getDeclaredFields(); 
      String fldname = "";
      String getMetName = "";
      try{
	        for(Field field:fields){  
	        	fldname = field.getName();
	            getMetName = "get"+fldname.substring(0,1).toUpperCase()+fldname.substring(1); 
	            //System.out.println(getMetName);
	
	            Method method = cls.getMethod(getMetName);  
	            Object object= method.invoke(obj, new Object[]{});
	    		//System.out.println(fldname+"="+String.valueOf(object));
	            info("实体入参"+fldname+"=【"+String.valueOf(object)+"】");
	        }  
      }catch(Exception e){
      	e.printStackTrace();
      }
  }
}
