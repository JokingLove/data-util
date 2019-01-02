package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelTable("tb_web_site_info")
public class WebSiteInfo extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 788367481725890506L;

	/** 网站id  **/
//	@ExcelField
//	private Long id;
	
	/** 网站标识  **/
	@ExcelField
	private String siteInfoId;
	
	/** 年报id  **/
	@ExcelField
	private String yearReportId;
	
	/** 网站网店类型  **/
	@ExcelField
	private String siteType;
	
	/** 网站名称  **/
	@ExcelField
	private String siteName;
	
	/** 网站网址  **/
	@ExcelField
	private String siteUrl;
	
}
