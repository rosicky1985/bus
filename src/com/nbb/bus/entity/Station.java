package com.nbb.bus.entity;

public class Station {
	private String id;
	private String longitude;
	private String latitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public static Station fromString(String line) {
		Station s = new Station();
		String[] ss = line.split("\\s+");
		s.setId(ss[0]);
		s.setLongitude(ss[1]);
		s.setLatitude(ss[2]);
		return s;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", longitude=" + longitude + ", latitude="
				+ latitude + "]";
	}
}
