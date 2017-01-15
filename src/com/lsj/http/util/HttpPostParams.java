package com.lsj.http.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpPostParams extends HttpParams{

	@Override
	public String Send(String base) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			String value = (String) params.get(key);
			formparams.add(new BasicNameValuePair(key, value));
		}
		HttpPost request = new HttpPost(base);
		request.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");		//内容为post
		CloseableHttpResponse response = httpClient.execute(request);
		return ReadInputStream(response.getEntity().getContent());
	}
}
