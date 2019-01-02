package com.pingansec.bean.excel;

import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ExcelTable("tb_hlj_shop")
public class HljShop extends BaseBean{

		private static final long serialVersionUID = 2073978602269826027L;
		
		@ExcelField
		private String num;
		
		@ExcelField(len = 3306)
		private String shopName;
		
		@ExcelField
		private String shopUrl;
		
		@ExcelField
		private String platformName;
		
		@ExcelField
		private String companyName;
		
		@ExcelField
		private String companyCode;
		
		@ExcelField
		private String phone;
		
		@ExcelField
		private String openTime;
		
		@ExcelField
		private String shopType;
		
		@ExcelField 
		private String compareResult;
		
		@ExcelField
		private String relevance;
		
		@ExcelField
		private String orgName;
		
		@ExcelField
		private String area;
		
}
