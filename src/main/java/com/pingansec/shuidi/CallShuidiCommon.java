package com.pingansec.shuidi;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingansec.utils.HttpClientUtil;
import com.pingansec.utils.SecureUtil;

/**
 * 公用的调用水滴查询企业信息
 * @author lilon
 * 2016-06-01
 */
public class CallShuidiCommon {
	
	static Logger logger = LoggerFactory.getLogger(CallShuidiCommon.class);
	
	final static int ALLOW_QUERY_COMPANY_NAME_LEN_MIN = 3;
	
	/**
	 * 根据企业名称查询企业信息
	 * @param enterprise
	 * @return
	 * @throws Exception
	 */
	public static CompanyMessage getShuidiInfo(String enterprise) throws Exception {
		if(StringUtils.isBlank(enterprise) || (!StringUtils.isBlank(enterprise) && enterprise.length() <= ALLOW_QUERY_COMPANY_NAME_LEN_MIN)) return null;
		// 当前时间
		long ptime = System.currentTimeMillis();
		// 验证码
		String vkey = SecureUtil.md5(ShuidiCfg.getInstance().pkey.concat("_").concat(ptime + "").concat("_").concat(ShuidiCfg.getInstance().pkey));
		
		StringBuffer _StringBuffer = new StringBuffer();
		//合作方身份识别
		_StringBuffer.append("pname=" + ShuidiCfg.getInstance().pname + "&");
//		_StringBuffer.append("pname=bjgs&");
		//时间戳
		_StringBuffer.append("ptime=" + ptime + "&");
		//企业名称转换
//		enterprise = HtmlUtils.htmlUnescape(URLEncoder.encode(enterprise, ShuidiCfg.getInstance().encode));
		//企业名称
		_StringBuffer.append("key=" + URLEncoder.encode(enterprise, ShuidiCfg.getInstance().encode) + "&");
//		_StringBuffer.append("key=45172fb8ecf74d73a3c90cae55d5c8af&");
		
		//安全验证码
		_StringBuffer.append("vkey=" + vkey);
		
		byte[] SendContent = _StringBuffer.toString().getBytes(ShuidiCfg.getInstance().encode);
		Hashtable<String, String> _Header = new Hashtable<String, String>();
		_Header.put("Content-Type", "application/x-www-form-urlencoded;charset=" + ShuidiCfg.getInstance().encode);
		
		logger.info(String.format("[PTIME=->%s 通过水滴接口查询企业信息 - 相关参数]->", ptime + enterprise) + _StringBuffer.toString());
		InputStream _InputStream = HttpClientUtil.sendMessage(SendContent, _Header, ShuidiCfg.getInstance().baseInfoUrl);
		
		CompanyMessage comp = null;
		String result = null;
		if (_InputStream != null) {
			result = HttpClientUtil.getResponseString(_InputStream, ShuidiCfg.getInstance().encode);
		}
		long endtime = System.currentTimeMillis();
		long usetime = (endtime - ptime);
		if (result != null) {
			String status = "ERR";
			try {
				CompanyMessage companyMessage = getCompanyMessageByJsonStr(result);
				logger.error("QueryShuidiAct::[Entity->CompanyMessage]获取到的内容-->== " + result + " == ");
				if (companyMessage != null) {
					comp = companyMessage;
					status = "OK";
				} else {
					status = "-查询后解析JSON企业信息为NULL-";
				}
			} catch (Exception ex) {
				status = "ERR";
				ex.printStackTrace();
				logger.error("QueryShuidiAct::[Entity->CompanyMessage解析失败]获取到的内容-->== " + result + " == ");
			}
			logger.info(String.format("[PTIME=->%s 查询状态->%s 耗时->%s 毫秒！", ptime + enterprise, status, usetime));
		} else {
			logger.info(String.format("[PTIME=->%s 查询失败（响应流返回NULL） 耗时->%s 毫秒！", ptime + enterprise, usetime));
		}
		return comp;
	}
	
	public static CompanyMessage getCompanyMessageByJsonStr(String result) throws Exception {
		CompanyMessage companyMessage = null;
		if(StringUtils.isBlank(result)){
			return null;
		}
		try {
			JSONObject object = JSON.parseObject(result);
			Object companyObj = object.get("company");
			JSONObject object2 = null;
			if(companyObj == null ){
				/**sd 2017-05-25 新接口修改   guoyq 2018-04-04 新接口修改*/
				Object res = object.get("statusCode");
				if (res != null && res.toString().trim().equals("1")) {
					Object dataObj = object.get("data");
					if (dataObj != null ) {
						object2 = JSON.parseObject(dataObj.toString());
						if (object2 != null) {
							companyObj = object2.get("base");
						}
					}
				}
			/*Object data = object.get("data");
			JSONObject dataJson = (JSONObject)data;
			companyObj = dataJson.get("company");*/
			}
			if (companyObj != null) {
				companyMessage = new CompanyMessage();
				JSONObject companyJsonObj = (JSONObject) companyObj;
				companyMessage.setAuthority(companyJsonObj.getString("authority"));
				companyMessage.setBusinessScope(companyJsonObj.getString("businessScope"));//
				companyMessage.setCapital(companyJsonObj.getString("capital"));//
				companyMessage.setCompanyAddress(companyJsonObj.getString("companyAddress"));//
				companyMessage.setCompanyName(companyJsonObj.getString("companyName"));
				companyMessage.setCompanyStatus(companyJsonObj.getString("companyStatus"));//
				companyMessage.setCompanyType(companyJsonObj.getString("companyType"));//
				companyMessage.setEstablishDate(companyJsonObj.getString("establishDate"));//
				companyMessage.setLegalPerson(companyJsonObj.getString("legalPerson"));
				companyMessage.setOperationStartdate(companyJsonObj.getString("businessDateFrom"));//
				companyMessage.setOperationEnddate(companyJsonObj.getString("businessDateTo"));//
				companyMessage.setUrl(object.getString("url"));
				if(StringUtils.isNotBlank(companyJsonObj.getString("creditNo"))){
					companyMessage.setCompanyCode(companyJsonObj.getString("creditNo"));
				}else{
					companyMessage.setCompanyCode(companyJsonObj.getString("companyCode"));
				}
//				companyMessage.setPhone(object2.get("companyPhone").toString());
				companyMessage.setProvince(companyJsonObj.getString("province"));
				companyMessage.setResultCode(CompanyMessage.SUCCESS);
			}
		} catch (Exception e) {
			throw e;
		}
		return companyMessage;
	}
	
	public static void main(String[] args) {
		String str = "二连市卓琴贸易有限公司";
		try {
			CompanyMessage shuidiInfo = getShuidiInfo(str);
			System.out.println(shuidiInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
