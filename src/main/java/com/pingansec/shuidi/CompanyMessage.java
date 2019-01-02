package com.pingansec.shuidi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyMessage implements Serializable {

	public static final int SUCCESS = 0; //获取数据成功
	private static final long serialVersionUID = 1L;
	private String authority; // 登记机关
	private String businessScope; // 经营范围
	private String capital; // 注册资本
	private String companyAddress; // 注册地址
	private String companyCode; // 注册号
	private String companyName; // 公司名称
	private String companyStatus; // 登记状态
	private String companyType; // 公司类型
	private String establishDate; // 成立日期
	private String legalPerson; // 法定代表
	private String operationStartdate; // 经营开始日期
	private String operationEnddate; // 经营结束日期
	private String province;
	private String url; // 水滴详情页链接
	private String phone;
	

	private List<ChangeMessage> changeList = new ArrayList<ChangeMessage>();
	private List<ExecuteMessage> executeList = new ArrayList<ExecuteMessage>();
	private List<PartnerMessage> partnerList = new ArrayList<PartnerMessage>();
	
	private Integer resultCode; 
	private String message; 

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	public String getOperationStartdate() {
		return operationStartdate;
	}

	public void setOperationStartdate(String operationStartdate) {
		this.operationStartdate = operationStartdate;
	}

	public String getOperationEnddate() {
		return operationEnddate;
	}

	public void setOperationEnddate(String operationEnddate) {
		this.operationEnddate = operationEnddate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ChangeMessage> getChangeList() {
		return changeList;
	}

	public void setChangeList(List<ChangeMessage> changeList) {
		this.changeList = changeList;
	}

	public List<ExecuteMessage> getExecuteList() {
		return executeList;
	}

	public void setExecuteList(List<ExecuteMessage> executeList) {
		this.executeList = executeList;
	}

	public List<PartnerMessage> getPartnerList() {
		return partnerList;
	}

	public void setPartnerList(List<PartnerMessage> partnerList) {
		this.partnerList = partnerList;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((businessScope == null) ? 0 : businessScope.hashCode());
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((changeList == null) ? 0 : changeList.hashCode());
		result = prime * result + ((companyAddress == null) ? 0 : companyAddress.hashCode());
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((companyStatus == null) ? 0 : companyStatus.hashCode());
		result = prime * result + ((companyType == null) ? 0 : companyType.hashCode());
		result = prime * result + ((establishDate == null) ? 0 : establishDate.hashCode());
		result = prime * result + ((executeList == null) ? 0 : executeList.hashCode());
		result = prime * result + ((legalPerson == null) ? 0 : legalPerson.hashCode());
		result = prime * result + ((operationEnddate == null) ? 0 : operationEnddate.hashCode());
		result = prime * result + ((operationStartdate == null) ? 0 : operationStartdate.hashCode());
		result = prime * result + ((partnerList == null) ? 0 : partnerList.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyMessage other = (CompanyMessage) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (businessScope == null) {
			if (other.businessScope != null)
				return false;
		} else if (!businessScope.equals(other.businessScope))
			return false;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (changeList == null) {
			if (other.changeList != null)
				return false;
		} else if (!changeList.equals(other.changeList))
			return false;
		if (companyAddress == null) {
			if (other.companyAddress != null)
				return false;
		} else if (!companyAddress.equals(other.companyAddress))
			return false;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (companyStatus == null) {
			if (other.companyStatus != null)
				return false;
		} else if (!companyStatus.equals(other.companyStatus))
			return false;
		if (companyType == null) {
			if (other.companyType != null)
				return false;
		} else if (!companyType.equals(other.companyType))
			return false;
		if (establishDate == null) {
			if (other.establishDate != null)
				return false;
		} else if (!establishDate.equals(other.establishDate))
			return false;
		if (executeList == null) {
			if (other.executeList != null)
				return false;
		} else if (!executeList.equals(other.executeList))
			return false;
		if (legalPerson == null) {
			if (other.legalPerson != null)
				return false;
		} else if (!legalPerson.equals(other.legalPerson))
			return false;
		if (operationEnddate == null) {
			if (other.operationEnddate != null)
				return false;
		} else if (!operationEnddate.equals(other.operationEnddate))
			return false;
		if (operationStartdate == null) {
			if (other.operationStartdate != null)
				return false;
		} else if (!operationStartdate.equals(other.operationStartdate))
			return false;
		if (partnerList == null) {
			if (other.partnerList != null)
				return false;
		} else if (!partnerList.equals(other.partnerList))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CompanyMessage [authority=" + authority + ", businessScope=" + businessScope + ", capital=" + capital
				+ ", companyAddress=" + companyAddress + ", companyCode=" + companyCode + ", companyName=" + companyName
				+ ", companyStatus=" + companyStatus + ", companyType=" + companyType + ", establishDate="
				+ establishDate + ", legalPerson=" + legalPerson + ", operationStartdate=" + operationStartdate
				+ ", operationEnddate=" + operationEnddate + ", url=" + url + ", changeList=" + changeList
				+ ", executeList=" + executeList + ", partnerList=" + partnerList + ",resultCode=" + resultCode + ",message=" + message + "]";
	}

}
