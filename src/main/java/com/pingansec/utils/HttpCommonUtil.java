package com.pingansec.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * http请求通用工具类
 * 
 * @author lzy
 *
 */
public class HttpCommonUtil {
	
	static Logger logger = LoggerFactory.getLogger(HttpCommonUtil.class);
	
	/**
	 * 通用http请求方法(SpringMVC 包含上传文件)
	 * @param requestUrl
	 * @param multipartEntityBuilder
	 * @return
	 */
	public static Map<String, Object> excuteMultipartHttpRequest(String requestUrl, HttpEntity httpEntity) {
		HttpPost post = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			post = new HttpPost(requestUrl);
			RequestConfig requestConfig = HttpCommonUtil.getRequestConfig();
			post.setConfig(requestConfig);
			httpClient = HttpClients.createDefault();
			post.addHeader("Connection", "Keep-Alive");
			post.addHeader("Charset", "UTF-8");
			post.setEntity(httpEntity);
			logger.info(String.format("【HttpCommonUtil】执行http请求 | START!"));
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			map.put("code", response.getStatusLine().getStatusCode());
			JSONObject obj = JSONObject.parseObject(EntityUtils.toString(entity));
			map.put("result", obj);
			return map;
		} catch (Exception e) {
			logger.error(String.format("【HttpCommonUtil】http请求异常 | error：%s", e.getMessage()));
		} finally {
			try {
				// 释放连接
				post.releaseConnection();
				httpClient.close();
				response.close();
			} catch (IOException e) {
				logger.error(String.format("【HttpCommonUtil】释放httpClient异常 | error：%s", e.getMessage()));
			}
		}
		return map;
	}
	
	
	/**
	 * 通用http请求方法(SpringMVC 包含上传文件)
	 * @param requestUrl
	 * @param multipartEntityBuilder
	 * @return
	 */
	public static String excuteFileMultipartHttpRequest(String requestUrl, HttpEntity httpEntity) {
		HttpPost post = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			post = new HttpPost(requestUrl);
			RequestConfig requestConfig = getRequestConfig();
			post.setConfig(requestConfig);
			httpClient = HttpClients.createDefault();
			post.addHeader("Connection", "Keep-Alive");
			post.addHeader("Charset", "UTF-8");
			post.setEntity(httpEntity);
			logger.info(String.format("【HttpCommonUtil】 执行http请求 | START!"));
			response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String returnStr = EntityUtils.toString(entity);
					logger.info(String.format("【HttpCommonUtil】 回调成功！结果 | result：%s",returnStr));
					return returnStr;
				}
			}
			
		} catch (Exception e) {
			logger.error(String.format("【HttpCommonUtil】 请求异常 | error：%s", e.getMessage()));
		} finally {
			try {
				// 释放连接
				post.releaseConnection();
				httpClient.close();
				response.close();
			} catch (IOException e) {
				logger.error(String.format("【HttpCommonUtil】 释放httpClient异常 | error：%s", e.getMessage()));
			}
		}
		return null;
	}
	
	/**
	 * 获取http请求超时时间配置
	 * @return
	 */
	public static RequestConfig getRequestConfig() {
		Integer overTime = 30000;
		Integer requestTimeout = 30000;
		Integer socketTime = 30000;
		RequestConfig requestConfig = RequestConfig.custom()
		.setConnectTimeout(overTime)
		.setConnectionRequestTimeout(requestTimeout)
		.setSocketTimeout(socketTime)
		.build();
		return requestConfig;
	}
}
