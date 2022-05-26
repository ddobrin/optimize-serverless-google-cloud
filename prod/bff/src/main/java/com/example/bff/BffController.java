package com.example.bff;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BffController {

  // logger
  private static final Logger logger = LoggerFactory.getLogger(BffController.class);

  // Instantiate OkHttpClient
  private static final OkHttpClient ok =
      new OkHttpClient.Builder()
          .readTimeout(500, TimeUnit.MILLISECONDS)
          .writeTimeout(500, TimeUnit.MILLISECONDS)
          .build();

  @Value("${quotes_url}")
  String quotesURL;

  @GetMapping("/quotes") 
  public ResponseEntity<String> allQuotes() throws IOException{
    if(quotesURL==null){
      logger.error("Quotes Service URL has not been configured. Please set the QUOTES_URL env variable");
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // retrieve all quotes
    ResponseBody quotes = makeAuthenticatedRequest(quotesURL, "quotes");
    return new ResponseEntity<String>(quotes.string(), HttpStatus.OK);
  }

  @PostMapping("/quotes")
  public ResponseEntity<String> createQuote(@RequestBody Data data) throws IOException { 
    logger.info("Quote: " + data);

    // build a Quote
    Quote quote = new Quote();
    quote.setId(data.getId());
    quote.setAuthor(data.getAuthor());
    quote.setQuote(data.getQuote());

    ObjectMapper mapper = new ObjectMapper();
    String quoteString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quote);

    ResponseBody quotes = makeAuthenticatedPostRequest(quotesURL, "quotes", quoteString);
    return new ResponseEntity<String>(quotes.string(), HttpStatus.OK);    
  }

  @DeleteMapping("/quotes/{id}")
  public ResponseEntity<HttpStatus> deleteQuote(@PathVariable("id") Integer id) {
    logger.info("Delete by ID: " + id);

    String path = String.format("%s/%s", "/quotes", id.toString());
    ResponseBody status = makeAuthenticatedDeleteRequest(quotesURL, path);
    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);    
  }

  public ResponseBody makeAuthenticatedRequest(String url, String path) {
    ResponseBody respBody = null;
    String target = String.format("%s/%s", url, path);

    try {
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
      respBody = response.body();
    } catch (IOException e) {
      logger.error("Unable to get Quotes service data", e);
    }
    return respBody;
  }

  public ResponseBody makeAuthenticatedPostRequest(String url, String path, String data) {
    ResponseBody respBody = null;
    String target = String.format("%s/%s", url, path);

    try {
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
      respBody = response.body();
    } catch (IOException e) {
      logger.error("Unable to POST to the Quotes service", e);
    }
    return respBody;
  }  

  public ResponseBody makeAuthenticatedDeleteRequest(String url, String path) {
    ResponseBody respBody = null;
    String target = String.format("%s/%s", url, path);

    try {
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
      respBody = response.body();
    } catch (IOException e) {
      logger.error("Unable to get Quotes service data", e);
    }
    return respBody;
  }  
}
