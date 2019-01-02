package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 年报-农专
 * @author JOKING
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelTable("tb_year_report_farmer_info")
public class YearReportFarmerInfo extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 788367481725890506L;

	/** 网站id  **/
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
	
	/** 合作社名称  **/
	@ExcelField
	private String cooperativeName;
	
	/**  成员人数  **/
	@ExcelField
	private String memberNum;
	
	/** 成员中农民数  **/
	@ExcelField
	private String farmerNum;
	
	/** 本年度新增成员人数  **/
	@ExcelField
	private String newMemeberNum;
	
	/** 本年度退出成员人数  **/
	@ExcelField
	private String exitMemeberNum;
	
	/** 企业联系电话  **/
	@ExcelField
	private String contactNum;
	
	/** 电子邮箱 **/
	@ExcelField
	private String email;
	
}
