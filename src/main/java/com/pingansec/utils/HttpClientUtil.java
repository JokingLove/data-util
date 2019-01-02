package com.pingansec.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * httpClient
 * @author lilon
 *
 */
@SuppressWarnings({ "unused", "deprecation" })
public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private final static String ENCODE = "utf-8";
	
	private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 60000;
  
    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(30);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        // configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

	/**
	 * POST发送消息
	 * 
	 * @param Content
	 *            发送的内容
	 * @param _Header
	 *            HTTP头信息
	 * @return HTTPResponse应答
	 */
	public static InputStream sendMessage(byte[] Content, Hashtable<String, String> _Header, String serverAddress) {
		return SendMessage(Content, _Header, serverAddress, null, 0, null, null);
	}
	
	public static InputStream SendMessage(byte[] Content, Hashtable<String, String> _Header, String serverAddress, String proxyHost, int proxyPort, String userName, String password) {
		InputStream _InputStream = null;
		
		try {
			if (serverAddress == null)
				return null;

			HttpPost _HttpPost = new HttpPost(serverAddress);
			if (_Header != null) {
				Enumeration<String> _List = _Header.keys();
				String keyString = "";
				try {
					while (_List.hasMoreElements()) {
						keyString = _List.nextElement();
						_HttpPost.addHeader(keyString, _Header.get(keyString));
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				}
			}
			_HttpPost.addHeader("Connection", "Keep-Alive");
			_HttpPost.setEntity(new ByteArrayEntity(Content));
			
			_HttpPost.setConfig(requestConfig);
			
			
			HttpClient _HttpClient = null;
			if (proxyPort == 0) {
				_HttpClient = getHttpClient();
			} else {
				_HttpClient = getHttpClient(proxyHost, proxyPort, userName, password);
			}
			HttpResponse _HttpResponse = _HttpClient.execute(_HttpPost);

			if (_Header != null) {
				_Header.put("ResponseContentLength", String.valueOf(_HttpResponse.getEntity().getContentLength()));
				Header[] _RespHeader = _HttpResponse.getAllHeaders();
				if (_RespHeader != null && _RespHeader.length > 0) {
					for (Header header : _RespHeader) {
						_Header.put(header.getName(), header.getValue());
					}
				}
			}
			_InputStream = _HttpResponse.getEntity().getContent();
		} catch (Exception e) {
			logger.error("请求失败，原因：" + e.getMessage() + "请求地址：" + serverAddress, e);
		}
		return _InputStream;
	}

	/**
	 * Get发送消息
	 * 
	 * @param Content
	 *            发送的内容
	 * @param _Header
	 *            HTTP头信息
	 * @return HTTPResponse应答
	 */
	public static InputStream sendMessage(Hashtable<String, String> _Header, String serverAddress) {
		return sendMessage(_Header, serverAddress, null, 0, null, null);
	}

	public static InputStream sendMessage(Hashtable<String, String> _Header, String serverAddress, String proxyHost, int proxyPort, String userName, String password) {
		InputStream _InputStream = null;
		try {
			if (serverAddress == null)
				return null;

			int indexOf = serverAddress.indexOf("http://");
			if (indexOf != 0)
				indexOf = serverAddress.indexOf("https://");

			HttpGet _HttpGet = new HttpGet(indexOf == 0 ? serverAddress : "http://" + serverAddress);
			if (_Header != null) {
				Enumeration<String> _List = _Header.keys();
				String keyString = "";
				try {
					while (_List.hasMoreElements()) {
						keyString = _List.nextElement();
						_HttpGet.addHeader(keyString, _Header.get(keyString));
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				}
			}
			_HttpGet.addHeader("Connection", "Keep-Alive");
			HttpClient _HttpClient = null;
			if (proxyPort == 0) {
				_HttpClient = getHttpClient();
			} else {
				_HttpClient = getHttpClient(proxyHost, proxyPort, userName, password);
			}
			HttpResponse _HttpResponse = _HttpClient.execute(_HttpGet);
			if (_Header != null) {
				_Header.put("ResponseContentLength", String.valueOf(_HttpResponse.getEntity().getContentLength()));
				Header[] _RespHeader = _HttpResponse.getAllHeaders();
				if (_RespHeader != null && _RespHeader.length > 0) {
					for (Header header : _RespHeader) {
						_Header.put(header.getName(), header.getValue());
					}
				}
			}
			_InputStream = _HttpResponse.getEntity().getContent();
		} catch (Exception e) {
			logger.error("请求失败，原因：" + e.getMessage() + "地址:" + serverAddress, e);
		}
		return _InputStream;
	}
	
	/** 
     * 发送 SSL POST 请求（HTTPS），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {  
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        CloseableHttpResponse response = null;  
        String httpStr = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }
    
    /** 
     * 创建SSL安全连接 
     * 
     * @return 
     */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {  
        SSLConnectionSocketFactory sslsf = null;  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
  
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                    return true;  
                }  
            }).build();  
           /* sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {  
  
                public boolean verify(String arg0, SSLSession arg1) {  
                    return true;  
                }  
  
                @Override  
                public void verify(String host, SSLSocket ssl) throws IOException {  
                }  
  
                @Override  
                public void verify(String host, X509Certificate cert) throws SSLException {  
                }  
  
                @Override  
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {  
                }  
            });  */
        } catch (GeneralSecurityException e) {  
            e.printStackTrace();  
        }  
        return sslsf;  
    }

	/**
	 * 
	 * @param _InputStream
	 * @return
	 */
	public static String getResponseString(InputStream _InputStream) {
		String response = "error:";
		try {
			if (_InputStream != null) {
				StringBuffer buffer = new StringBuffer();
				InputStreamReader isr = new InputStreamReader(_InputStream, ENCODE);
				Reader in = new BufferedReader(isr);
				int ch;
				while ((ch = in.read()) > -1) {
					buffer.append((char) ch);
				}
				response = buffer.toString();
				buffer = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * @param sm
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static byte[] getResponseBytes(InputStream sm, int length) throws IOException {
		ByteArrayOutputStream baos = getResponseStream(sm, length);
		return baos.toByteArray();
	}

	public static ByteArrayOutputStream getResponseStream(InputStream sm, int length) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
		byte[] tempCatch = new byte[1024];
		int readCount = 0;
		while ((readCount = sm.read(tempCatch, 0, tempCatch.length)) != -1) {
			baos.write(tempCatch, 0, readCount);
		}
		return baos;
	}

	/**
	 * 
	 * @param _InputStream
	 * @param Charset
	 * @return
	 */
	public static String getResponseString(InputStream _InputStream, String Charset) {
		String response = "error:";
		try {
			if (_InputStream != null) {
				StringBuffer buffer = new StringBuffer();
				InputStreamReader isr = new InputStreamReader(_InputStream, Charset);
				Reader in = new BufferedReader(isr);
				int ch;
				while ((ch = in.read()) > -1) {
					buffer.append((char) ch);
				}
				response = buffer.toString();
				buffer = null;
			} else {
				response = response + "timeout";
			}
		} catch (Exception e) {
			logger.error("获取响应错误，原因：" + e.getMessage());
			response = response + e.getMessage();
		}
		return response;
	}

	public static String getGZIPString(InputStream _InputStream, String carset) {
		String content = "";
		try {
			StringBuffer buffer = new StringBuffer();
			GZIPInputStream _GZIPInputStream = new GZIPInputStream(_InputStream);
			InputStreamReader _InputStreamReader = new InputStreamReader(_GZIPInputStream, "utf-8");
			int ch = 0;
			while ((ch = _InputStreamReader.read()) > -1) {
				buffer.append((char) ch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public static void setGZIP(ByteArrayOutputStream _ByteArrayOutputStream, byte[] Content) {

	}
	
	private static HttpClient getHttpClient() {
		return HttpClients.createMinimal(connMgr);
	}
	
	private static HttpClient getHttpClient(String proxyHost, int proxyPort, String userName, String password) {
		return getHttpClient();
	}
	
	
}
