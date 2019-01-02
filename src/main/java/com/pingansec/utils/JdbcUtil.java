package com.pingansec.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pingansec.bean.JdbcBean;


public class JdbcUtil {
	
	public static final Logger log = LoggerFactory.getLogger(JdbcUtil.class);
	public static final String DEFAULT_DB_CONFIG_FILE = "db.properties";
	
	
	public static final String DEFAULT_CONFIG			 = "default";
	public static final String DEFAULT_CONFIG_DRIVER	 = "db.driver";
	public static final String DEFAULT_CONFIG_URL 		 = "db.url";
	public static final String DEFAULT_CONFIG_USERNAME   = "db.username";
	public static final String DEFAULT_CONFIG_PASSWORD   = "db.password";
	
	public static final String DEFAULT_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	
	public static final String SPLITOR = "[.]";
	
	
/*	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;*/
	
	private static Map<String, JdbcBean> jdbcBeans;
	
	static {
		try {
			jdbcBeans = new HashMap<>();
			InputStream inputStream = ClassLoader.getSystemResourceAsStream(DEFAULT_DB_CONFIG_FILE);
			Properties prop = new Properties();
			prop.load(inputStream);
			// 分类获取 jdbc 配置
			Enumeration<Object> keys = prop.keys();
			while(keys.hasMoreElements()) {
				String key = (String)keys.nextElement();
				int index = key.indexOf(DEFAULT_CONFIG_DRIVER);
				if(StringUtils.hasText(key) && index > -1) {
					String type = StringUtils.EMPTY;
					if(index > 0) {
						type = key.substring(0, index);
					}
					JdbcBean jdbcBean = new JdbcBean();
					String driverClass = prop.getProperty(key);
					jdbcBean.setDriver(key, driverClass);
					jdbcBean.setUrl(type + DEFAULT_CONFIG_URL, prop.getProperty(type + DEFAULT_CONFIG_URL));
					jdbcBean.setUsername(type + DEFAULT_CONFIG_USERNAME, prop.getProperty(type + DEFAULT_CONFIG_USERNAME));
					jdbcBean.setPassword(type + DEFAULT_CONFIG_PASSWORD, prop.getProperty(type + DEFAULT_CONFIG_PASSWORD));
					if(StringUtils.isEmpty(type)) type = DEFAULT_CONFIG;
					type = type.replaceAll(SPLITOR, StringUtils.EMPTY);
					jdbcBean.setType(type);
					jdbcBeans.put(type, jdbcBean);
					// 加载驱动
					Class.forName(driverClass);
					log.info(String.format("读取到配置：%s", jdbcBean));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("read db.properties file error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("load driver class error: " + e.getMessage());
		}
	}
	
	public static Connection getConnection() {
		return getConnection(DEFAULT_CONFIG);
	}
	
	public static synchronized Connection getConnection(String type) {
		Connection connection = null;
		JdbcBean jdbcBean = jdbcBeans.get(type);
		if(jdbcBean == null)  throw new NullPointerException(String.format("对不起没有配置的数据库：%s", type));
		String url = jdbcBean.getUrl();
		String username = jdbcBean.getUsername();
		String password = jdbcBean.getPassword();
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			log.error(String.format("get database connection error: " + e.getMessage()), e);
		}
		return connection;
	}
	
	/**
	 * 手动开启事务
	 * @param connection
	 */
	public static synchronized void beginTransaction(Connection connection) {
		if(connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (Exception e) {
				log.error(String.format("开启事务失败： %s", e.getMessage()), e);
			}
		}
	}
	
	/**
	 * 提交事务
	 * @param connectin
	 */
	public static synchronized void commitTransaction(Connection connection) {
		if(connection != null) {
			try {
				connection.commit();
			} catch (Exception e) {
				log.error(String.format("提交事务失败： %s", e.getMessage()), e);
			}
		}
	}
	
	public static synchronized void commit(Connection connection) {
		if(connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				log.error(String.format("connection commit error： %s", e.getMessage()), e);
			}
		}
	}
	
	public static synchronized void close(Connection connection) {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		}catch (Exception e) {
			log.error("close connection error: " + e.getMessage());
		}
	}

	public static synchronized void close(Statement statement) {
		try {
			if(statement != null && !statement.isClosed()) {
				statement.close();
			}
		}catch (Exception e) {
			log.error("close prepareStatement error: " + e.getMessage());
		}
	}
	
	public static synchronized void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) {
				rs.close();
			}
		}catch (Exception e) {
			log.error("close prepareStatement error: " + e.getMessage());
		}
	}

	public static void close(Connection connection, Statement statement, ResultSet rs) {
		if(connection != null) close(connection);
		if(statement != null) close(statement);
		if(rs != null) close(rs);
	}
	
/*	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/aic_hlj?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&zeroDateTimeBehavior=convertToNull";
		Connection connection = DriverManager.getConnection(url, "root", "root");
		System.out.println(connection);
	}*/
	
}
