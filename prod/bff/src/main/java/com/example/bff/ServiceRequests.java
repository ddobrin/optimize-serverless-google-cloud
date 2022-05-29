package com.example.bff;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ServiceRequests {
    static ResponseBody makeAuthenticatedRequest(OkHttpClient ok, String url, String path) throws IOException {
        ResponseBody respBody = null;
        String target = String.format("%s/%s", url, path);

        // get ID token
        String token = getToken(target);

        // Instantiate HTTP request
        Request request =
            new Request.Builder()
                .url(target)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    
        Response response = ok.newCall(request).execute();
        return response.body();
      }

    static ResponseBody makeAuthenticatedPostRequest(OkHttpClient ok, String url, String path, String data) throws IOException {
        ResponseBody respBody = null;
        String target = String.format("%s/%s", url, path);

        // get ID token
        String token = getToken(target);

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

    static Response makeAuthenticatedDeleteRequest(OkHttpClient ok, String url, String path) throws IOException {
        ResponseBody respBody = null;
        String target = String.format("%s/%s", url, path);

        // Retrieve Application Default Credentials
        String token = getToken(target);

        Request request =
            new Request.Builder()
                .url(target)
                .addHeader("Authorization", "Bearer " + token)
                .delete()
                .build();
    
        Response response = ok.newCall(request).execute();
        return response;
      }

    private static String getToken(String target) throws IOException {
        // Retrieve Application Default Credentials
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        IdTokenCredentials tokenCredentials =
                IdTokenCredentials.newBuilder()
                        .setIdTokenProvider((IdTokenProvider) credentials)
                        .setTargetAudience(target)
                        .build();

        // Create an ID token
        return tokenCredentials.refreshAccessToken().getTokenValue();
    }
}
