package com.nbb.bus.dianping;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nbb.bus.dianping.entity.bussiness.Bussiness;
import com.nbb.bus.dianping.entity.bussiness.Response;
import com.nbb.bus.entity.Station;

public class Http {
	private static Logger logger = Logger.getLogger(Http.class);

	public static String http(final Station s) throws ClientProtocolException,
			IOException {
		CloseableHttpClient client = HttpClients.custom().build();
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response)
					throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				String content = entity != null ? EntityUtils.toString(entity)
						: null;
				logger.debug(content);
				Response r = createGson().fromJson(content, Response.class);
				String cont = null;
				if (r.getStatus().equals("OK")) {
					if (r.getBusinesses().size() != 0) {
						Bussiness first = r.getBusinesses().get(0);
						cont = s.getId() + "," + first;
						logger.debug(cont);
					} else {
						logger.warn(s.getId() + "附件无商户");
					}
				}
				return cont;
			}
		};
		Map<String, String> paramMap = paras(s);
		RequestBuilder build = RequestBuilder.get().setUri(
				"http://api.dianping.com/v1/business/find_businesses");
		build.addParameter("appkey", "1410091924");
		build.addParameter("sign", sign(paramMap));
		for (Entry<String, String> entry : paramMap.entrySet()) {
			build.addParameter(entry.getKey(), entry.getValue());
		}
		HttpUriRequest request = build.build();
		logger.debug(request.toString());
		return client.execute(request, responseHandler);
	}

	private static Map<String, String> paras(Station s) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("category", "美食");
		paramMap.put("city", "上海");
		paramMap.put("latitude", s.getLatitude());
		paramMap.put("longitude", s.getLongitude());
		paramMap.put("sort", "7");
		paramMap.put("limit", "20");
		paramMap.put("offset_type", "1");
		paramMap.put("out_offset_type", "1");
		paramMap.put("page", "1");
		paramMap.put("platform", "2");
		// paramMap.put("radius", "50");
		return paramMap;
	}

	public static String sign(Map<String, String> paramMap) {
		String appKey = "1410091924";
		String secret = "a454fe0964134f76aa71071e4fdf0601";
		StringBuilder stringBuilder = new StringBuilder();
		// 对参数名进行字典排序
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);
		// 拼接有序的参数名-值串
		stringBuilder.append(appKey);
		for (String key : keyArray) {
			stringBuilder.append(key).append(paramMap.get(key));
		}
		String codes = stringBuilder.append(secret).toString();
		String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes)
				.toUpperCase();
		return sign;
	}

	private static Gson createGson() {
		GsonBuilder gsonbuilder = new GsonBuilder();
		Gson gson = gsonbuilder.serializeNulls().create();
		return gson;
	}
}
