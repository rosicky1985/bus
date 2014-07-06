package com.nbb.bus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.nbb.bus.dianping.Http;
import com.nbb.bus.entity.Station;

public class Bus_Station {
	private static Logger logger = Logger.getLogger(Bus_Station.class);

	public static void main(String[] args) throws IOException {
		// dianping();
		gaode_type();
	}

	private static void gaode_type() throws ClientProtocolException,
			IOException {
		int count = 1;
		List<String> result = new ArrayList<String>();
		String[] types = { "餐饮", "酒店", "医院", "学校", "电影院", "超市", "商场", "银行",
				"景点", "公交站" };
		for (String s : FileUtils.readLines(new File(
				"/home/rosicky/bus_station/data/stations"), "utf-8")) {
			Station st = Station.fromString(s);
			logger.info(count++ + "processing... " + st.getId());
			StringBuilder sb = new StringBuilder();
			sb.append(st.getId()).append("=>");
			for (String type : types) {
				Long cnt = com.nbb.bus.gaode.Http.http(st, type, 1000);
				sb.append(type).append(":").append(cnt).append(",");
			}
			logger.info(sb.toString());
			result.add(sb.toString());
		}
		FileUtils
				.writeLines(
						new File(
								"/home/rosicky/bus_station/data/gaode_1000_categories"),
						result);
	}

	private static void dianping() throws IOException, ClientProtocolException {
		int count = 1;
		List<String> result = new ArrayList<String>();
		for (String s : FileUtils.readLines(new File(
				"/home/rosicky/bus_station/data/stations"), "utf-8")) {
			Station st = Station.fromString(s);
			logger.info(count++ + "processing... " + st.getId());
			result.add(Http.http(st));
		}
		FileUtils
				.writeLines(
						new File(
								"/home/rosicky/bus_station/data/stations_firstbussiness_category"),
						result);
	}
}
