package com.nbb.bus.gaode;

import java.io.IOException;
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
import com.nbb.bus.entity.Station;
import com.nbb.bus.gaode.entity.Response;

public class Http {
	private static Logger logger = Logger.getLogger(Http.class);

	public static Long http(final Station s, String keyword, Integer range)
			throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.custom().build();
		ResponseHandler<Long> responseHandler = new ResponseHandler<Long>() {
			public Long handleResponse(final HttpResponse response)
					throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				String content = entity != null ? EntityUtils.toString(entity)
						: null;
				logger.debug(content);
				content = content.replace("callback", "");// 去掉callback
				content = content.substring(1, content.length() - 1);// 去掉头尾的括号
				Response r = createGson().fromJson(content, Response.class);
				return r == null ? 0
						: (r.getTotal());
			}

			private Gson createGson() {
				GsonBuilder gsonbuilder = new GsonBuilder();
				Gson gson = gsonbuilder.serializeNulls().create();
				return gson;
			}
		};
		Map<String, String> paramMap = paras(s, keyword, range);
		RequestBuilder build = RequestBuilder
				.get()
				.setUri("http://s.amap.com/web/ws/mapapi/poi/info/?version=2.0&channel=openapi_pc&from=AMAP&index=true&language=zh_cn&query_type=RQBXY&city=310000&output=jsonp&pagesize=10&pagenum=1&qii=true&_=1404569524371");
		for (Entry<String, String> entry : paramMap.entrySet()) {
			build.addParameter(entry.getKey(), entry.getValue());
		}
		HttpUriRequest request = build.build();
		logger.debug(request.toString());
		return client.execute(request, responseHandler);
	}

	private static Map<String, String> paras(Station s, String keyword,
			Integer range) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keywords", keyword);
		paramMap.put("latitude", s.getLatitude());
		paramMap.put("longitude", s.getLongitude());
		paramMap.put("range", range.toString());
		paramMap.put("sign", "85BC89CD63494E7C7016458C86C8C6C6");
		paramMap.put("ts", "1404645502");
		return paramMap;
	}
}
