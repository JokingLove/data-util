package com.pingansec.shuidi;

import java.io.Serializable;

public class ExecuteMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	private String execGov; // 执行法院
	private String execStatus; // 状态
	private String execSubject; // 执行标的
	private String filingDate; // 立案时间
	private String filingNo; // 案号

	public String getExecGov() {
		return execGov;
	}

	public void setExecGov(String execGov) {
		this.execGov = execGov;
	}

	public String getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}

	public String getExecSubject() {
		return execSubject;
	}

	public void setExecSubject(String execSubject) {
		this.execSubject = execSubject;
	}

	public String getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(String filingDate) {
		this.filingDate = filingDate;
	}

	public String getFilingNo() {
		return filingNo;
	}

	public void setFilingNo(String filingNo) {
		this.filingNo = filingNo;
	}

	@Override
	public String toString() {
		return "ExecuteMessage [execGov=" + execGov + ", execStatus=" + execStatus + ", execSubject=" + execSubject
				+ ", filingDate=" + filingDate + ", filingNo=" + filingNo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((execGov == null) ? 0 : execGov.hashCode());
		result = prime * result + ((execStatus == null) ? 0 : execStatus.hashCode());
		result = prime * result + ((execSubject == null) ? 0 : execSubject.hashCode());
		result = prime * result + ((filingDate == null) ? 0 : filingDate.hashCode());
		result = prime * result + ((filingNo == null) ? 0 : filingNo.hashCode());
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
		ExecuteMessage other = (ExecuteMessage) obj;
		if (execGov == null) {
			if (other.execGov != null)
				return false;
		} else if (!execGov.equals(other.execGov))
			return false;
		if (execStatus == null) {
			if (other.execStatus != null)
				return false;
		} else if (!execStatus.equals(other.execStatus))
			return false;
		if (execSubject == null) {
			if (other.execSubject != null)
				return false;
		} else if (!execSubject.equals(other.execSubject))
			return false;
		if (filingDate == null) {
			if (other.filingDate != null)
				return false;
		} else if (!filingDate.equals(other.filingDate))
			return false;
		if (filingNo == null) {
			if (other.filingNo != null)
				return false;
		} else if (!filingNo.equals(other.filingNo))
			return false;
		return true;
	}

}
