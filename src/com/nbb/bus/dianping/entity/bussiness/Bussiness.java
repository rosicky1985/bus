package com.nbb.bus.dianping.entity.bussiness;

import java.util.List;

public class Bussiness {
	private Integer distance;
	private List<String> regions;
	private String address;
	private String name;

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public List<String> getRegions() {
		return regions;
	}

	public void setRegions(List<String> regions) {
		this.regions = regions;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Bussiness [distance=" + distance + ", regions=" + regions
				+ ", address=" + address + ", name=" + name + "]";
	}
}
