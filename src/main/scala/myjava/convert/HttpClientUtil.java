package myjava.convert;

import myjava.log.MSLog;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
	
	private static HttpClientUtil clientUtil ;
	
	private static CloseableHttpClient httpClient = null;

	static {
		//
		httpClient = HttpClients.createDefault();
		//
		clientUtil = new HttpClientUtil();
	}

	private int status = 0;

	/**
	 * 私有化构造方法
	 */
	private HttpClientUtil() {
		
	}
	
	/**
	 * 获取当前对象，单例对象
	 * @return
	 */
	public static HttpClientUtil getInstance() {
		
		return clientUtil; 
	}

	/**
	 * POST提交
	 * @param strUrl 请求URL
	 * @param strJson 请求json字符串
	 * @return
	 */
	public String postJson(String strUrl,String strJson) {
		
		String body = null;
		if (strJson != null && !"".equals(strJson.trim())) {
			
			try {
				//实体
				StringEntity entity = new StringEntity(strJson.toString(),"utf-8");
				//post
				HttpPost postMethod = new HttpPost(strUrl);
				postMethod.setEntity(entity);
				
				long startTime = System.currentTimeMillis();
				
				//响应
				CloseableHttpResponse response = httpClient.execute(postMethod);
				long endTime = System.currentTimeMillis();
				int statusCode = response.getStatusLine().getStatusCode();

				MSLog.info("statusCode:" + statusCode);
				MSLog.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));

				if (statusCode != HttpStatus.SC_OK) {
					MSLog.error("Method failed:" + response.getStatusLine());
					status = 1;
				}

				// Read the response body
				body = EntityUtils.toString(response.getEntity());

			} catch (IOException e) {
				// 发生网络异常
				MSLog.error("exception occurred：",e);
				// 网络错误
				status = 3;
			} finally {
				MSLog.info("调用接口状态：" + status);
			}
		}
		
		return body;
	}
	

	/**
	 * POST提交
	 * @param strUrl 请求URL
//	 * @param strJson 请求json字符串
	 * @return
	 */
	public String postMap(String strUrl,Map<Object, Object> paraMap) {
		
		String body = null;
		
		if (paraMap != null && paraMap.size() > 0) {
			
			try {
				
				/**  设置请求参数 */
				//实体
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				//赋值
				for (Object key : paraMap.keySet()) {
					nvps.add(new BasicNameValuePair(key.toString(), (String)paraMap.get(key)));  
				}
				
				//post
				HttpPost postMethod = new HttpPost(strUrl);
				postMethod.setEntity(new UrlEncodedFormEntity(nvps,	"utf-8"));  
				//请求时间
				long startTime = System.currentTimeMillis();
				//响应
				CloseableHttpResponse response = httpClient.execute(postMethod);
				long endTime = System.currentTimeMillis();
				int statusCode = response.getStatusLine().getStatusCode();

				MSLog.info("statusCode:" + statusCode);
				MSLog.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));

				if (statusCode != HttpStatus.SC_OK) {
					MSLog.error("Method failed:" + response.getStatusLine());
					status = 1;
				}
				// Read the response body
				body = EntityUtils.toString(response.getEntity());

			} catch (IOException e) {
				// 发生网络异常
				MSLog.error("exception occurred：",e);
				// 网络错误
				status = 3;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MSLog.info("调用接口状态：" + status);
			}
		}
		
		return body;
	}
	
	
	

}
