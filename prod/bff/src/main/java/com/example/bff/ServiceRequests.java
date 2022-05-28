package com.example.bff;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;

import org.springframework.beans.factory.annotation.Value;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ServiceRequests {
    @Value("${read_timeout:5000}")
    String readTimeout;
    @Value("${write_timeout:5000}")
    String writeTimeout;
  
    static long read;
    static long write;
  
    @Value("${read_timeout:5000}")
    public void setRead(String readTimeout){ read = Long.valueOf(readTimeout);}; 
    @Value("${write_timeout:5000}")
    public void setWrite(String writeTimeout){ read = Long.valueOf(writeTimeout);}; 
  
    // Instantiate OkHttpClient
    private static final OkHttpClient ok =
        new OkHttpClient.Builder()
            .readTimeout(read, TimeUnit.MILLISECONDS)
            .writeTimeout(write, TimeUnit.MILLISECONDS)
            .build();
  
    static ResponseBody makeAuthenticatedRequest(String url, String path) throws IOException {
        ResponseBody respBody = null;
        String target = String.format("%s/%s", url, path);
    
        // Retrieve Application Default Credentials
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        IdTokenCredentials tokenCredentials =
            IdTokenCredentials.newBuilder()
                .setIdTokenProvider((IdTokenProvider) credentials)
                .setTargetAudience(target)
                .build();
    
        // Create an ID token
        String token = tokenCredentials.refreshAccessToken().getTokenValue();
        // Instantiate HTTP request
        // MediaType contentType = MediaType.get("application/json; charset=utf-8");
        // okhttp3.RequestBody body = okhttp3.RequestBody.create(data, contentType);
        Request request =
            new Request.Builder()
                .url(target)
                .addHeader("Authorization", "Bearer " + token)
                //.post(body)
                .build();
    
        Response response = ok.newCall(request).execute();
        return response.body();
      }
    
      static ResponseBody makeAuthenticatedPostRequest(String url, String path, String data) throws IOException {
        ResponseBody respBody = null;
        String target = String.format("%s/%s", url, path);
    
        // Retrieve Application Default Credentials
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        IdTokenCredentials tokenCredentials =
            IdTokenCredentials.newBuilder()
                .setIdTokenProvider((IdTokenProvider) credentials)
                .setTargetAudience(target)
                .build();
    
        // Create an ID token
        String token = tokenCredentials.refreshAccessToken().getTokenValue();
        // Instantiate HTTP request
        MediaType contentType = MediaType.get("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(data, contentType);
        Request request =
            new Request.Builder()
                .url(target)
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();
    
        Response response = ok.newCall(request).execute();
        return respBody = response.body();
      }  
    
      static Response makeAuthenticatedDeleteRequest(String url, String path) throws IOException {
        ResponseBody respBody = null;
        String target = String.format("%s/%s", url, path);
    
    
        // Retrieve Application Default Credentials
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        IdTokenCredentials tokenCredentials =
            IdTokenCredentials.newBuilder()
                .setIdTokenProvider((IdTokenProvider) credentials)
                .setTargetAudience(target)
                .build();
    
        // Create an ID token
        String token = tokenCredentials.refreshAccessToken().getTokenValue();
        Request request =
            new Request.Builder()
                .url(target)
                .addHeader("Authorization", "Bearer " + token)
                .delete()
                .build();
    
        Response response = ok.newCall(request).execute();
        return response;
      }      
}
