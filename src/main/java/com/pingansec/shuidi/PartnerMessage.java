package com.pingansec.shuidi;

import java.io.Serializable;

public class PartnerMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String stockCapital; // 应交出资额
	private String stockName; // 股东名称
	private String stockRealcapital; // 实交出资额
	private String stockType; // 股东类型

	public String getStockCapital() {
		return stockCapital;
	}

	public void setStockCapital(String stockCapital) {
		this.stockCapital = stockCapital;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockRealcapital() {
		return stockRealcapital;
	}

	public void setStockRealcapital(String stockRealcapital) {
		this.stockRealcapital = stockRealcapital;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stockCapital == null) ? 0 : stockCapital.hashCode());
		result = prime * result + ((stockName == null) ? 0 : stockName.hashCode());
		result = prime * result + ((stockRealcapital == null) ? 0 : stockRealcapital.hashCode());
		result = prime * result + ((stockType == null) ? 0 : stockType.hashCode());
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
		PartnerMessage other = (PartnerMessage) obj;
		if (stockCapital == null) {
			if (other.stockCapital != null)
				return false;
		} else if (!stockCapital.equals(other.stockCapital))
			return false;
		if (stockName == null) {
			if (other.stockName != null)
				return false;
		} else if (!stockName.equals(other.stockName))
			return false;
		if (stockRealcapital == null) {
			if (other.stockRealcapital != null)
				return false;
		} else if (!stockRealcapital.equals(other.stockRealcapital))
			return false;
		if (stockType == null) {
			if (other.stockType != null)
				return false;
		} else if (!stockType.equals(other.stockType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PartnerMessage [stockCapital=" + stockCapital + ", stockName=" + stockName + ", stockRealcapital="
				+ stockRealcapital + ", stockType=" + stockType + "]";
	}

}
