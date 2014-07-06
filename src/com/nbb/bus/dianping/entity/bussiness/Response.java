package com.nbb.bus.dianping.entity.bussiness;

import java.util.List;

public class Response {
	private String status;
	private Integer count;
	private Integer total_count;
	private List<Bussiness> businesses;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}
	public List<Bussiness> getBusinesses() {
		return businesses;
	}
	public void setBusinesses(List<Bussiness> businesses) {
		this.businesses = businesses;
	}
}
