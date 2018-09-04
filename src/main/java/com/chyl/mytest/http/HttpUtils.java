package com.chyl.mytest.http;


import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chyl
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static final CloseableHttpClient httpClient;
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url) {
        String body = "";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse httpresponse = httpClient.execute(httpget);
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity, Consts.UTF_8);
            httpget.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Request {} error", url, e);
        }

        return body;
    }

    public static String doGet1(String url) {
        String body = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Connection", "close");
        try {
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity, Consts.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Request {} error", url, e);
        } finally {
            try {
                if (httpget != null) {
                    httpget.releaseConnection();
                }
                if (response != null) {
                    response.close();
                    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        httpget.abort();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return body;
    }

    public static String doGet(String url, List<NameValuePair> params) {
        try {
            String param = EntityUtils.toString(new UrlEncodedFormEntity(params));
            StringBuilder stringBuilder = new StringBuilder(url);
            if (!url.contains("?")) {
                stringBuilder.append("?");
            } else {
                stringBuilder.append("&");
            }
            stringBuilder.append(param);
            return doGet(stringBuilder.toString());
        } catch (IOException e) {
            logger.error("Request {} error", url, e);
        }
        return "";
    }

    public static String doPost(String url, Map<String, String> paramsMap) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String body = null;
        try {
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String[] keys = paramsMap.keySet().toArray(new String[0]);
            for(String key : keys){
                params.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            httppost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            HttpResponse httpresponse = httpClient.execute(httppost);
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            httppost.releaseConnection();
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            logger.error("Request {} error", url, e);
        } catch (IOException e) {
            logger.error("Request {} error", url, e);
        }
        return body;
    }

    /**
     * @param url
     * @param json
     * @return
     */
    public static String doPostForJson(String url, String json) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String result = "";
        try {
            StringEntity s = new StringEntity(json, Consts.UTF_8);
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = httpClient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(res.getEntity(), Consts.UTF_8);
            }
            post.releaseConnection();
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            logger.error("Request {} error", url, e);
        } catch (IOException e) {
            logger.error("Request {} error", url, e);
        }

        return result;
    }
}

