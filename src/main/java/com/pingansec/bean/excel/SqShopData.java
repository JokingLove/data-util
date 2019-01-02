package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelTable("tb_sq_shop")
public class SqShopData extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExcelField(len = 512)
	private String shopName;
	@ExcelField(len = 512)
	private String domain;
	@ExcelField
	private String ip;
	// 网站物理地址
	@ExcelField
	private String siteAddress;
	// 网站经营者类型
	@ExcelField
	private String shopType;
	@ExcelField
	private String ecommerceType;
	// 网站载体类型
	@ExcelField
	private String websiteType;
	// 电子标识
	@ExcelField
	private String identification;
	// 放心消费电子标识
	@ExcelField
	private String consumeIdentification;
	// 入库人
	@ExcelField
	private String addPerson;
	// 入库时间
	@ExcelField
	private String addTime;
	// 备注
	@ExcelField
	private String remark;
	// 企业名称
	@ExcelField(len = 512)
	private String companyName;
	// 注册号
	@ExcelField
	private String registNum;
	// 联系人
	@ExcelField
	private String contactor;
	// 联系电话
	@ExcelField
	private String phone;
	// 联系地址
	@ExcelField
	private String address;
	// 电子邮箱
	@ExcelField
	private String email;
	// 注册资本
	@ExcelField
	private String registCapital;
	// 成立日期
	@ExcelField
	private String establishDate;
	// 经营期限
	@ExcelField
	private String operatPeriod;
	// 经营范围
	@ExcelField(len = 2048)
	private String businessScope;
	// 监管单位
	@ExcelField
	private String orgName;
	// 登记机关
	@ExcelField
	private String authority;
	
}
