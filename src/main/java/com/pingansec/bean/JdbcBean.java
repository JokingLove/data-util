package com.pingansec.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * jdbc 连接数据库字符串
 * @author JOKING
 */
public class JdbcBean extends BaseBean{

	private static final long serialVersionUID = -645241968477115062L;
	

	
	private String type;
	private PropEntry driver;
	private PropEntry url;
	private PropEntry username;
	private PropEntry password;
	
	private Set<String> keys = new HashSet<>();
	
	public JdbcBean setType(String type) {
		this.type = type;
		return this;
	}
	
	public JdbcBean setDriver(String driverKey, String value) {
		this.driver = new PropEntry(driverKey, value);
		keys.add(driverKey);
		return this;
	}

	public JdbcBean setUrl(String urlKey, String value) {
		this.url = new PropEntry(urlKey, value);
		keys.add(urlKey);
		return this;
	}



	public JdbcBean setUsername(String usernameKey, String value) {
		this.username = new PropEntry(usernameKey, value);
		keys.add(usernameKey);
		return this;
	}



	public JdbcBean setPassword(String passwordKey, String value) {
		this.password = new PropEntry(passwordKey, value);
		keys.add(passwordKey);
		return this;
	}

	public String getType() {
		return type;
	}



	public String getDriver() {
		return driver.getValue();
	}
	
	public String getDriverKey() {
		return driver.getKey();
	}



	public String getUrl() {
		return url.getValue();
	}
	
	public String getUrlKey() {
		return url.getKey();
	}



	public String getUsername() {
		return username.getValue();
	}
	public String getUsernameKey() {
		return username.getKey();
	}



	public String getPassword() {
		return password.getValue();
	}
	public  String getPasswordKey() {
		return password.getKey();
	}


    class PropEntry{
		private String key;
		private String value;
		
		public PropEntry(String key, String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "PropEntry [key=" + key + ", value=" + value + "]";
		}
	}

	@Override
	public String toString() {
		return "JdbcBean [type=" + type + ", driver=" + driver + ", url=" + url + ", username=" + username
				+ ", password=" + password + ", keys=" + keys + "]";
	}
    
}
