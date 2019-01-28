package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;
import lombok.Data;

@Data
@ExcelTable("tb_company_and_site_info")
public class CompanyAndSiteInfo extends BaseBean {
	@ExcelField(value="", index = 0)
    private Long id;
    /** 主体身份代码  */
    @ExcelField(value = "", index = 0)
    private String identityCode;
    /** 企业名称  */
    @ExcelField(value = "", index = 0)
    private String companyName;
    /** 社会统一信用代码 */
    @ExcelField(value = "", index = 0)
    private String creditCode;
    /** 企业类型  */
    @ExcelField(value = "", index = 0)
    private String companyType;
    /** 成立日期 */
    @ExcelField(value = "", index = 0)
    private String establishmentDate;
    /** 登记机关  */
    @ExcelField(value = "", index = 0)
    private String registrationAuthority;
    /** 营业期限自 */
    @ExcelField(value = "", index = 0)
    private String businessDateFrom;
    /** 营业期限至  */
    @ExcelField(value = "", index = 0)
    private String businessDateTo;
    /** 登记状态 */
    @ExcelField(value = "", index = 0)
    private String registrationStatus;
    /** 行政区划 */
    @ExcelField(value = "", index = 0)
    private String postCode;
    /** 注册资本 */
    @ExcelField(value = "", index = 0)
    private String registedCapital;
    /** 注册号  */
    @ExcelField(value = "", index = 0)
    private String registNum;
    /** 地址  **/
    @ExcelField(value = "", index = 0)
    private String address;
    /** 法定代表人 */
    @ExcelField(value = "", index = 0)
    private String legalPerson;
    /** 联系电话 **/
    @ExcelField(value = "", index = 0)
    private String contactNum;
    /** 性别 **/
    @ExcelField(value = "", index = 0)
    private String gender;
    /** 经营范围  */
    @ExcelField(value = "", index = 0, len = 1024)
    private String businessScope;
    /** 网站类型 */
    @ExcelField(value = "", index = 0)
    private String siteType;
    /** 网站类型 */
    @ExcelField(value = "", index = 0)
    private String siteName;
    /** 网站网址 */
    @ExcelField(value = "", index = 0)
    private String siteUrl;
    /** 更新时间 */
    @ExcelField(value = "", index = 0)
    private String updateTime;
/*    *//** 已经处理  *//*
    @ExcelField(value = "", type=Integer.class, len=1)
    private int processed;*/
}
