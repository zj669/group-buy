package com.zj.groupbuy.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * HttpClient工具类，封装GET和POST请求
 * 支持同步和异步调用
 */
public class HttpClientUtil {
    
    private static final HttpClient httpClient;
    private static final int DEFAULT_TIMEOUT = 30; // 默认超时时间30秒
    
    static {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .build();
    }
    
    /**
     * 发送GET请求（同步）
     * 
     * @param url 请求URL
     * @return 响应结果
     * @throws IOException 请求异常
     * @throws InterruptedException 中断异常
     */
    public static HttpResponse<String> get(String url) throws IOException, InterruptedException {
        return get(url, null);
    }
    
    /**
     * 发送GET请求（同步）
     * 
     * @param url 请求URL
     * @param headers 请求头
     * @return 响应结果
     * @throws IOException 请求异常
     * @throws InterruptedException 中断异常
     */
    public static HttpResponse<String> get(String url, Map<String, String> headers) 
            throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .GET();
        
        // 添加请求头
        if (headers != null) {
            headers.forEach(builder::header);
        }
        
        HttpRequest request = builder.build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    /**
     * 发送GET请求（异步）
     * 
     * @param url 请求URL
     * @return CompletableFuture<HttpResponse<String>>
     */
    public static CompletableFuture<HttpResponse<String>> getAsync(String url) {
        return getAsync(url, null);
    }
    
    /**
     * 发送GET请求（异步）
     * 
     * @param url 请求URL
     * @param headers 请求头
     * @return CompletableFuture<HttpResponse<String>>
     */
    public static CompletableFuture<HttpResponse<String>> getAsync(String url, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .GET();
        
        // 添加请求头
        if (headers != null) {
            headers.forEach(builder::header);
        }
        
        HttpRequest request = builder.build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
    
    /**
     * 发送POST请求（同步）
     * 
     * @param url 请求URL
     * @param body 请求体
     * @return 响应结果
     * @throws IOException 请求异常
     * @throws InterruptedException 中断异常
     */
    public static HttpResponse<String> post(String url, String body) 
            throws IOException, InterruptedException {
        return post(url, body, null);
    }
    
    /**
     * 发送POST请求（同步）
     * 
     * @param url 请求URL
     * @param body 请求体
     * @param headers 请求头
     * @return 响应结果
     * @throws IOException 请求异常
     * @throws InterruptedException 中断异常
     */
    public static HttpResponse<String> post(String url, String body, Map<String, String> headers) 
            throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .POST(HttpRequest.BodyPublishers.ofString(body));
        
        // 设置默认Content-Type
        builder.header("Content-Type", "application/json");
        
        // 添加请求头
        if (headers != null) {
            headers.forEach(builder::header);
        }
        
        HttpRequest request = builder.build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    /**
     * 发送POST表单请求（同步）
     * 
     * @param url 请求URL
     * @param formData 表单数据
     * @return 响应结果
     * @throws IOException 请求异常
     * @throws InterruptedException 中断异常
     */
    public static HttpResponse<String> postForm(String url, Map<String, String> formData) 
            throws IOException, InterruptedException {
        return postForm(url, formData, null);
    }
    
    /**
     * 发送POST表单请求（同步）
     * 
     * @param url 请求URL
     * @param formData 表单数据
     * @param headers 请求头
     * @return 响应结果
     * @throws IOException 请求异常
     * @throws InterruptedException 中断异常
     */
    public static HttpResponse<String> postForm(String url, Map<String, String> formData, Map<String, String> headers) 
            throws IOException, InterruptedException {
        // 构建表单数据
        StringBuilder formBody = new StringBuilder();
        if (formData != null) {
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                if (formBody.length() > 0) {
                    formBody.append("&");
                }
                formBody.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .POST(HttpRequest.BodyPublishers.ofString(formBody.toString()));
        
        // 设置表单Content-Type
        builder.header("Content-Type", "application/x-www-form-urlencoded");
        
        // 添加请求头
        if (headers != null) {
            headers.forEach(builder::header);
        }
        
        HttpRequest request = builder.build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    /**
     * 发送POST请求（异步）
     * 
     * @param url 请求URL
     * @param body 请求体
     * @return CompletableFuture<HttpResponse<String>>
     */
    public static CompletableFuture<HttpResponse<String>> postAsync(String url, String body) {
        return postAsync(url, body, null);
    }
    
    /**
     * 发送POST请求（异步）
     * 
     * @param url 请求URL
     * @param body 请求体
     * @param headers 请求头
     * @return CompletableFuture<HttpResponse<String>>
     */
    public static CompletableFuture<HttpResponse<String>> postAsync(String url, String body, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .POST(HttpRequest.BodyPublishers.ofString(body));
        
        // 设置默认Content-Type
        builder.header("Content-Type", "application/json");
        
        // 添加请求头
        if (headers != null) {
            headers.forEach(builder::header);
        }
        
        HttpRequest request = builder.build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

}