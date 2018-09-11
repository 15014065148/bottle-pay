package com.eveb.bottlepay.common.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.eveb.bottlepay.common.exception.RRException;
import com.eveb.bottlepay.common.service.SysConfigService;

import lombok.Builder;
import lombok.ToString;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Component
public class OkHttpProxyUtils {

    private final static Logger log = LoggerFactory.getLogger(OkHttpProxyUtils.class);

    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";
    public final static String PATCH = "PATCH";

    private final static String UTF8 = "UTF-8";
    private final static String GBK = "GBK";

    public final static String APPLICATION_JSON = "application/json";
    public final static String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private final static String SELECT_CODE = "proxy";

    private final static String DEFAULT_CHARSET = UTF8;
    private final static String DEFAULT_METHOD = GET;
    private final static boolean DEFAULT_LOG = true;

    public OkHttpClient client;

    //***波音请求实例，加入拦截器及代理*//*
    public OkHttpClient bbinclient;

    public OkHttpProxyUtils(@Qualifier("sysConfigService") SysConfigService sysConfigService) {
        List<String> proxylist = sysConfigService.getProxys(SELECT_CODE, null);

        client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            }
        }).connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS).build();

        OkHttpClient.Builder bbinbuilder = new OkHttpClient.Builder();

        if (proxylist.size() > 0) {
            bbinbuilder.proxySelector(new ProxySelector() {
                @Override
                public List<Proxy> select(URI uri) {
                    List<Proxy> list = new ArrayList<>();
                    for (String proxystr : proxylist) {
                        String[] proxys = proxystr.split(",");
                        if (proxys[0].equals("http")) {
                            list.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxys[1], Integer.parseInt(proxys[2]))));
                        } else {
                            list.add(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxys[1], Integer.parseInt(proxys[2]))));
                        }
                    }
                    return list;
                }

                @Override
                public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

                }
            });
        }
        bbinbuilder.addInterceptor(newBBINInterceptor());
        bbinbuilder.connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES));
        bbinbuilder.readTimeout(20, TimeUnit.SECONDS);
        bbinclient = bbinbuilder.connectTimeout(20, TimeUnit.SECONDS).build();

        //huiTongPayClient = getHuiTongPayClient(new OkHttpClient.Builder(), sysConfigService);
    }
    
    public String get(OkHttpClient httpClient, String url) {
        return execute(OkHttp.builder().url(url).build(),httpClient);
    }

    /**
     * @param httpClient
     * @param url
     * @param charset
     * @return
     */
    public  String get(OkHttpClient httpClient, String url, String charset) {
        return execute(OkHttp.builder().url(url).responseCharset(charset).build(),httpClient);
    }

    /**
     * @param httpClient
     * @param url
     * @param queryMap
     * @return
     */
    public  String get(OkHttpClient httpClient, String url, Map<String, String> queryMap) {
        return execute(OkHttp.builder().url(url).queryMap(queryMap).build(),httpClient);
    }

    /**
     * @param httpClient
     * @param url
     * @param queryMap
     * @param charset
     * @return
     */
    public  String get(OkHttpClient httpClient, String url, Map<String, String> queryMap, String charset) {
        return execute(OkHttp.builder().url(url).queryMap(queryMap).responseCharset(charset).build(),httpClient);
    }

    /**
     * @param httpClient
     * @param url
     * @param obj
     * @return
     */
    public  String postJson(OkHttpClient httpClient, String url, Object obj) {
        return execute(OkHttp.builder().url(url).method(POST).data(JSON.toJSONString(obj)).mediaType(APPLICATION_JSON).build(),httpClient);
    }

    /**
     * POST
      application/x-www-form-urlencoded
      @param url
      @param formMap
      @return
     */
    public  String postForm(OkHttpClient httpClient, String url, Map<String, String> formMap) {
        String data = "";
        if (MapUtils.isNotEmpty(formMap)) {
            data = formMap.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors
                    .joining("&"));
        }
        return execute(OkHttp.builder().url(url).method(POST).data(data).mediaType(APPLICATION_FORM_URLENCODED).build(),httpClient);
    }

    private  String post(OkHttpClient httpClient, String url, String data, String mediaType, String charset) {
        return execute(OkHttp.builder().url(url).method(POST).data(data).mediaType(mediaType).responseCharset(charset).build(),httpClient);
    }

    public Interceptor newBBINInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                int maxRetry = 3;//最大重试次数
                int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
                String rs = "";
                Request request = chain.request();
                Response response = chain.proceed(request);
                MediaType mediaType = response.body().contentType();
                if (response.isSuccessful()) {
                    rs = response.body().string();
                    Map rsmap = (Map) JSON.parse(rs);
                    String result = rsmap.get("result").toString();
                    //**API正忙，此情况下需要重试**//
                    if (result.equals("false")) {
                        String code = ((Map) rsmap.get("data")).get("Code").toString();
                        if (ArrayUtils.contains(new String[]{"10015","44004"}, code)) {
                            while (retryNum < maxRetry) {
                                response = chain.proceed(request);
                                retryNum++;
                                System.out.println(response.body().string());
                            }
                        }
                    }
                }
                return response.newBuilder()
                        .body(ResponseBody.create(mediaType, rs))
                        .build();
            }
        };
        return interceptor;
    }

    //**
     // 通用执行方法
     //*
    private  String execute(OkHttp okHttp, OkHttpClient httpClient) {
        if (StringUtils.isBlank(okHttp.requestCharset)) {
            okHttp.requestCharset = DEFAULT_CHARSET;
        }
        if (StringUtils.isBlank(okHttp.responseCharset)) {
            okHttp.responseCharset = DEFAULT_CHARSET;
        }
        if (StringUtils.isBlank(okHttp.method)) {
            okHttp.method = DEFAULT_METHOD;
        }
        if (StringUtils.isBlank(okHttp.mediaType)) {
            okHttp.mediaType = APPLICATION_JSON;
        }
        if (okHttp.requestLog) {//记录请求日志
            log.info(okHttp.toString());
        }

        String url = okHttp.url;

        Request.Builder builder = new Request.Builder();

        if (MapUtils.isNotEmpty(okHttp.queryMap)) {
            String queryParams = okHttp.queryMap.entrySet().stream()
                    .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("&"));
            url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", queryParams);
        }
        builder.url(url);

        if (MapUtils.isNotEmpty(okHttp.headerMap)) {
            okHttp.headerMap.forEach(builder::addHeader);
        }

        String method = okHttp.method.toUpperCase();
        String mediaType = String.format("%s;charset=%s", okHttp.mediaType, okHttp.requestCharset);

        if (StringUtils.equals(method, GET)) {
            builder.get();
        } else if (ArrayUtils.contains(new String[]{POST, PUT, DELETE, PATCH}, method)) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), okHttp.data);
            builder.method(method, requestBody);
        } else {
                throw new RRException(String.format("http method:%s not support!", method));
        }
        String result;
        try {
            Response response = httpClient.newCall(builder.build()).execute();
            result = response.body().string();
            if (okHttp.responseLog) {//记录返回日志
                log.info(String.format("Got response->%s", result));
            }
        } catch (Exception e) {
            log.error(okHttp.toString(), e);
            return null;
        }
        return result;
    }

    @Builder
    @ToString(exclude = {"requestCharset", "responseCharset", "requestLog", "responseLog"})
    static class OkHttp {
        private String url;
        private String method = DEFAULT_METHOD;
        private String data;
        private String mediaType = APPLICATION_JSON;
        private Map<String, String> queryMap;
        private Map<String, String> headerMap;
        private String requestCharset = DEFAULT_CHARSET;
        private boolean requestLog = DEFAULT_LOG;

        private String responseCharset = DEFAULT_CHARSET;
        private boolean responseLog = DEFAULT_LOG;
    }
}
