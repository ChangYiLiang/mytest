package com.chyl.mytest.http;



import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author chyl
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String doGet(String url) {
        String body = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpGet httpget = new HttpGet(url);
        try {
            httpResponse = httpClient.execute(httpget);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                httpget.abort();
            }
            HttpEntity entity = httpResponse.getEntity();
            body = EntityUtils.toString(entity, Consts.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Request {} error", url, e);
        } finally {
            httpget.releaseConnection();
            close(httpResponse);
        }
        return body;
    }

    public static String doGet1(String url) {
        String body = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpGet httpget = new HttpGet(url);
        try {
            URL realUrl = new URL(url);
            if (realUrl.getProtocol().toLowerCase().equals("https")) {
                httpClient = wrapClient();
            } else {
                httpClient = HttpClientBuilder.create().build();
            }

            httpResponse = httpClient.execute(httpget);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                httpget.abort();
            }
            HttpEntity entity = httpResponse.getEntity();
            body = EntityUtils.toString(entity, Consts.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Request {} error", url, e);
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
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String body = null;
        HttpPost httppost = new HttpPost(url);
        try {
            List<NameValuePair> params = new ArrayList<>();
            String[] keys = paramsMap.keySet().toArray(new String[0]);
            for (String key : keys) {
                params.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            httppost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            httpResponse = httpClient.execute(httppost);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                httppost.abort();
            }
            HttpEntity entity = httpResponse.getEntity();
            body = EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            logger.error("Request {} error", url, e);
        } catch (IOException e) {
            logger.error("Request {} error", url, e);
        } finally {
            httppost.releaseConnection();
            close(httpResponse);
        }
        return body;
    }

    /**
     * @param url
     * @param json
     * @return
     */
    public static String doPostForJson(String url, String json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String result = "";
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(json, Consts.UTF_8);
            s.setContentType("application/json");
            post.setEntity(s);
            httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                result = EntityUtils.toString(httpResponse.getEntity(), Consts.UTF_8);
            } else {
                post.abort();
            }
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            logger.error("Request {} error", url, e);
        } catch (IOException e) {
            logger.error("Request {} error", url, e);
        } finally {
            post.releaseConnection();
            close(httpResponse);
        }
        return result;
    }


    /**
     * @param headerMap 请求头
     * @param url
     * @param json
     * @return
     */
    public static String doPostForJson(Map<String, String> headerMap, String url, String json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String result = "";
        HttpPost post = new HttpPost(url);
        try {
            //头部请求信息
            if (headerMap != null) {
                Iterator iterator = headerMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    post.setHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }
            StringEntity s = new StringEntity(json, Consts.UTF_8);
            s.setContentType("application/json");
            post.setEntity(s);
            httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                result = EntityUtils.toString(httpResponse.getEntity(), Consts.UTF_8);
            } else {
                post.abort();
            }
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            logger.error("Request {} error", url, e);
        } catch (IOException e) {
            logger.error("Request {} error", url, e);
        } finally {
            post.releaseConnection();
            close(httpResponse);
        }
        return result;
    }

    /**
     * 创建一个不进行正式验证的请求客户端对象 不用导入SSL证书
     *
     * @return HttpClient
     */
    public static CloseableHttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    private static void close(CloseableHttpResponse httpResponse) {
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Request {} error", e);
        }
    }
}

