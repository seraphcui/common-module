/*
 * Copyright (c) 2017. All rights reserved by Taimi Robot.
 * Created by yaocui on 17-11-21 下午3:32.
 */

package com.tmirob.medical.commonmodule.utility;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.tmirob.medical.commonmodule.model.utility.IConstants.AUTHORIZATION;


public class HttpSender {
    private HttpClient httpClient;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpSender(){
        httpClient = HttpClients.createDefault();
    }

    public void get(String url, String token) throws IOException{
        HttpGet httpGet = new HttpGet(url);
        if(token != null && !token.isEmpty()){
            httpGet.setHeader(AUTHORIZATION, token);
        }
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        logger.info("Response Status Line: {}", response.getStatusLine());
        if (entity != null) {
            logger.info("Response content length: {}", entity.getContentLength());
            logger.info("Response content: {}", entity);
        }
    }

    public void get(String url) throws IOException{
        get(url, "");
    }

    public void post(String url, List<NameValuePair> params, String token) throws IOException{
        HttpEntity entity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
        postCore(url, entity, token);
    }

    public void post(String url, List<NameValuePair> params) throws IOException{
        post(url, params, null);
    }

    public String postJson(String url, String jsonString, String token) throws IOException{
        return postJson(url, jsonString, token, ContentType.APPLICATION_JSON);
    }

    public String postJson(String url, String jsonString, String token, ContentType contentType) throws IOException{
        HttpEntity entity = new StringEntity(jsonString, contentType);
        return postCore(url, entity, token);
    }

    public String postJson(String url, String jsonString) throws IOException{
        return postJson(url, jsonString, null);
    }

    public void get(String url, ResponseHandler handler, String token) throws IOException{
        HttpGet httpGet = new HttpGet(url);
        if(token != null && !token.isEmpty()){
            httpGet.setHeader(AUTHORIZATION, token);
        }
        httpClient.execute(httpGet, handler);
    }

    public void get(String url, ResponseHandler handler) throws IOException{
        get(url, handler, null);
    }

    private String postCore(String url, HttpEntity entity, String token) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (token != null && !token.isEmpty()) {
            httpPost.setHeader(AUTHORIZATION, token);
        }
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        logger.info("Response StatusLine: {}", response.getStatusLine());
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            logger.info("Response content length: {}", entity.getContentLength());
            String responseString = EntityUtils.toString(resEntity, StandardCharsets.UTF_8);
            logger.info("Response content: {}", responseString);
            return responseString;
        }
        return "";
    }
}
