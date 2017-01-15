package com.lsj.http.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpGetParams extends HttpParams{

	@Override
	public String Send(String baseUrl) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URIBuilder uri = new URIBuilder(baseUrl);
		for (String key : params.keySet()) {
			String value = (String) params.get(key);
			uri.addParameter(key, value);
		}
		HttpUriRequest request = new HttpGet(uri.toString());
		CloseableHttpResponse response = httpClient.execute(request);
		return ReadInputStream(response.getEntity().getContent());
	}
}
