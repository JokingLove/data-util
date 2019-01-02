package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelTable("tb_company_base_info")
public class CompanyBaseInfo extends BaseBean{
	
	private static final long serialVersionUID = 2073978602269826027L;
	
	private Long id;
	/** 主体身份代码  */
	@ExcelField(value = "", index = 0)
	private String identityCode;
	/** 注册号  */
	@ExcelField(value = "", index = 0)
	private String registNum;
	/** 社会统一信用代码 */
	@ExcelField(value = "", index = 0)
	private String creditCode;
	/** 企业名称  */
	@ExcelField(value = "", index = 0)
	private String companyName;
	/** 企业类型  */
	@ExcelField(value = "", index = 0)
	private String companyType;
	/** 法定代表人 */
	@ExcelField(value = "", index = 0)
	private String legalPerson;
	/** 注册资本 */
	@ExcelField(value = "", index = 0)
	private String registedCapital;
	/** 成立日期 */
	@ExcelField(value = "", index = 0)
	private String establishmentDate;
	/** 营业期限自 */
	@ExcelField(value = "", index = 0)
	private String businessDateFrom;
	/** 营业期限至  */
	@ExcelField(value = "", index = 0)
	private String businessDateTo;
	/** 登记机关  */
	@ExcelField(value = "", index = 0)
	private String registrationAuthority;
	/** 登记状态 */
	@ExcelField(value = "", index = 0)
	private String registrationStatus;
	/** 地址  **/
	@ExcelField(value = "", index = 0)
	private String address;
	/** 经营范围  */
	@ExcelField(value = "", index = 0, len = 1024)
	private String businessScope;
	/** 联系电话 **/
	@ExcelField(value = "", index = 0)
	private String contactNum;

}
