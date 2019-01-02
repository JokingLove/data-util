package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 年报  -个体
 * @author JOKING
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelTable("tb_year_report_individual_info")
public class YearReportIndividualInfo extends BaseBean{
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
	
	/** 名称  **/
	@ExcelField
	private String companyName;
	
	/** 经营者姓名  **/
	@ExcelField
	private String businesserName;
	
	/** 企业联系电话  **/
	@ExcelField
	private String contactNum;
	
}
