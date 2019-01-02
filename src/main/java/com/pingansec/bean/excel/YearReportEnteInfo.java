package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelTable("tb_year_report_ente_info")
public class YearReportEnteInfo extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 788367481725890506L;
	
	private Long id;

	/** 年报id  **/
	@ExcelField
	private String yearReportId;
	
	/** 主体身份代码  */
	@ExcelField
	private String identityCode;
	
	/**  年报时间  **/
	@ExcelField
	private String yearReportDate;
	
	/** 年报年度  **/
	@ExcelField
	private String yearReportAnnual;
	
	/** 注册号  */
	@ExcelField
	private String registNum;
	
	/** 社会统一信用代码 */
	@ExcelField
	private String creditCode;
	
	/** 企业机构名称  **/
	@ExcelField
	private String companyName;
	
	/** 市场主体类型  **/
	@ExcelField
	private String companyType;
	
	/** 企业联系电话  **/
	@ExcelField
	private String contactNum;
	
	/** 企业通信地址  **/
	@ExcelField(len = 1024)
	private String address;
	
	/** 邮政编码  **/
	@ExcelField
	private String postalCode;
	
	/** 电子邮箱  **/
	@ExcelField
	private String email;
	
	/** 营业状态  **/
	@ExcelField
	private String businessStatus;
	
}
