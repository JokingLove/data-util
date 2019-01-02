package com.pingansec.shuidi;

import java.io.Serializable;

public class ChangeMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String changeDate; // 变更时间
	private String changeField; // 变更项目名称
	private String contentAfter; // 变更后
	private String contentBefore; // 变更前

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getChangeField() {
		return changeField;
	}

	public void setChangeField(String changeField) {
		this.changeField = changeField;
	}

	public String getContentAfter() {
		return contentAfter;
	}

	public void setContentAfter(String contentAfter) {
		this.contentAfter = contentAfter;
	}

	public String getContentBefore() {
		return contentBefore;
	}

	public void setContentBefore(String contentBefore) {
		this.contentBefore = contentBefore;
	}

	@Override
	public String toString() {
		return "Chang [changeDate=" + changeDate + ", changeField=" + changeField + ", contentAfter=" + contentAfter
				+ ", contentBefore=" + contentBefore + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changeDate == null) ? 0 : changeDate.hashCode());
		result = prime * result + ((changeField == null) ? 0 : changeField.hashCode());
		result = prime * result + ((contentAfter == null) ? 0 : contentAfter.hashCode());
		result = prime * result + ((contentBefore == null) ? 0 : contentBefore.hashCode());
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
		ChangeMessage other = (ChangeMessage) obj;
		if (changeDate == null) {
			if (other.changeDate != null)
				return false;
		} else if (!changeDate.equals(other.changeDate))
			return false;
		if (changeField == null) {
			if (other.changeField != null)
				return false;
		} else if (!changeField.equals(other.changeField))
			return false;
		if (contentAfter == null) {
			if (other.contentAfter != null)
				return false;
		} else if (!contentAfter.equals(other.contentAfter))
			return false;
		if (contentBefore == null) {
			if (other.contentBefore != null)
				return false;
		} else if (!contentBefore.equals(other.contentBefore))
			return false;
		return true;
	}

}
