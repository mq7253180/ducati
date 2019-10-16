package com.hce.global.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientHelper {
	private final static String ERR_MSG = "Abnormal HTTP Status Code: %s, URI: %s";

	public static String get(String url, Header[] headers) throws IOException {
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url);
			return getString(httpGet, headers);
		} finally {
			if(httpGet!=null)
				httpGet.abort();
		}
	}

	public static String post(String url, Header[] headers, List<NameValuePair> nameValuePairList) throws IOException {
		return post(url, headers, new UrlEncodedFormEntity(nameValuePairList, "UTF-8"));
	}

	public static String post(String url, Header[] headers, String body) throws IOException {
		return post(url, headers, new StringEntity(body));
	}

	public static String post(String url, Header[] headers, HttpEntity entity) throws IOException {
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			httpPost.setEntity(entity);
			return getString(httpPost, headers);
		} finally {
			if(httpPost!=null)
				httpPost.abort();
		}
	}

	public static void saveAsFile(String url, Header[] headers, String path) throws IOException {
		HttpClient client = new HttpClient();
		HttpMethodBase method = new GetMethod(url);
		OutputStream out = null;
		try {
			int statusCode = client.executeMethod(method);
			if(statusCode!=200) {
				throw new HttpResponseException(statusCode, String.format(ERR_MSG, statusCode, url));
			}
			byte[] buf = method.getResponseBody();
			out = new BufferedOutputStream(new FileOutputStream(path));
			out.write(buf);
			out.flush();
		} finally {
			if(out!=null)
				out.close();
			if(method!=null)
				method.abort();
		}
	}

	private static String getString(HttpUriRequest httpUriRequest, Header[] headers) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new BufferedInputStream(send(httpUriRequest, headers))));
			StringBuilder result = new StringBuilder();
			String line = null;
			while((line = br.readLine())!=null) {
				result.append(line);
			}
			return result.toString();
		} finally {
			if(br!=null)
				br.close();
		}
	}

	private static InputStream send(HttpUriRequest httpUriRequest, Header[] headers) throws ClientProtocolException, IOException {
		if(headers!=null&&headers.length>0)
			httpUriRequest.setHeaders(headers);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(httpUriRequest);
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode==200) {
			HttpEntity entity = response.getEntity();
			return entity.getContent();
		} else {
			throw new HttpResponseException(statusCode, String.format(ERR_MSG, statusCode, httpUriRequest.getURI()));
		}
	}

	public static String getRequestURIOrURL(HttpServletRequest request, String type) {
		StringBuffer url = "URI".equalsIgnoreCase(type)?new StringBuffer(request.getRequestURI()):request.getRequestURL();
		String s = null;
		Map<String, String[]> map = request.getParameterMap();
		if(map!=null&&map.size()>0) {
			Set<Entry<String, String[]>> set = map.entrySet();
			url.append("?");
			for(Entry<String, String[]> entry:set) {
				url.append(entry.getKey());
				url.append("=");
				String[] values = entry.getValue();
				url.append((values!=null&&values.length>0)?values[0]:"");
				url.append("&");
			}
			s = url.substring(0, url.length()-1);
		} else
			s = url.toString();
		return s;
	}

	public static void main(String[] args) throws IOException {
//		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(4);
//		nameValuePairList.add(new BasicNameValuePair("msg_signature", "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3"));
//		nameValuePairList.add(new BasicNameValuePair("timestamp", "1409659589"));
//		nameValuePairList.add(new BasicNameValuePair("nonce", "263014780"));
//		nameValuePairList.add(new BasicNameValuePair("echostr", "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ=="));
//		System.out.println(HttpClientHelper.post("http://localhost:8080/wechat/opencallbackmode", nameValuePairList));
//		System.out.println(HttpClientHelper.post("http://www.maqiangcgq.com/wechat/opencallbackmode", nameValuePairList));
//		System.out.println(HttpClientHelper.post("http://www.maqiangcgq.com/wechat/assistant", nameValuePairList));
//		Header[] headers = new Header[]{new BasicHeader("x-requested-with", "XMLHttpRequest")};
//		System.out.println(HttpClientHelper.post("http://localhost:8080/test", null, headers));
		HttpClientHelper.saveAsFile("http://mmbiz.qpic.cn/mmbiz/wc7YNPm3YxU84aB22zTIBXGUDcGwYIJruDrwgwFzdK5BuS86Iic1Zzeb7tgP4BfjEHpcq7GCzPT0aBXeThm63rw/0?", null, "H:/aaa.gif");
	}
}