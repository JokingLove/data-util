package com.pingansec.shuidi;

import java.io.Serializable;

public class ShuidiCfg implements Serializable{
	private static final long serialVersionUID = 1L;
	/**老版本接口*/
/*	public String baseInfoUrl = "http://www.shuidixy.com/auth/ic/baseinfo";
	public String pname = "shuidixy";
	public String pkey = "274ae1eba5835575c9009f2e4ab2f4f6";
	
	public String encode = "utf-8";*/
	/**新版本*/ 
/*	public String pkey = "9e6abc95026b45daac8edcd7d675ab64";
	public String pname = "360_baike";
	public String encode = "utf-8";*/
	/** 新版本  2018-04-04 **/
	public String pkey = "45172fb8ecf74d73a3c90cae55d5c8af";
	public String pname = "bjgs";
	public String encode = "utf-8";
	// 地址
//	public String baseInfoUrl = "http://shuidi.cn/auth/ic/baseinfo";
	public String baseInfoUrl = "http://api.shuidi.cn/open/company/detail";
	
	private static class ShuidiCfgHolder {
		private static ShuidiCfg instance = new ShuidiCfg();
	}
	
	public static ShuidiCfg getInstance() {
		return ShuidiCfgHolder.instance;
	}
}
