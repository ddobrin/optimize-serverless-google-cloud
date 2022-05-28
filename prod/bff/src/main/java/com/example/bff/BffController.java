package com.example.bff;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.data.Data;
import com.example.data.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BffController {
  // logger
  private static final Log logger = LogFactory.getLog(BffController.class);

  @Value("${read_timeout:5000}")
  String readTimeout;
  @Value("${write_timeout:5000}")
  String writeTimeout;

  static long read;
  static long write;

  @Value("${read_timeout:5000}")
  public void setRead(String readTimeout){ BffController.read = Long.valueOf(readTimeout);}; 
  @Value("${write_timeout:5000}")
  public void setWrite(String writeTimeout){ BffController.read = Long.valueOf(writeTimeout);}; 

  // Instantiate OkHttpClient
  private static final OkHttpClient ok =
      new OkHttpClient.Builder()
          .readTimeout(read, TimeUnit.MILLISECONDS)
          .writeTimeout(write, TimeUnit.MILLISECONDS)
          .build();

  @Value("${quotes_url}")
  String quotesURL;

  @Value("${reference_url}")
  String referenceURL;

  @PostConstruct
  public void init() {
      logger.info("BffController: Post Construct Initializer"); 
      if(referenceURL==null){
        logger.error("Reference Service URL has not been configured. Please set the QUOTES_URL env variable");
      }
  
      // retrieve all quotes
      try {
        ResponseBody metadata = makeAuthenticatedRequest(referenceURL, "metadata");

        logger.info("Metadata:" + metadata.string());
      } catch (IOException e) {
        logger.error("Unable to get Reference service data", e);
      }
  }

  @GetMapping("/quotes") 
  public ResponseEntity<String> allQuotes(){
    if(quotesURL==null){
      logger.error("Quotes Service URL has not been configured. Please set the QUOTES_URL env variable");
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // retrieve all quotes
    try {
      ResponseBody quotes = makeAuthenticatedRequest(quotesURL, "quotes");

      return new ResponseEntity<String>(quotes.string(), HttpStatus.OK);
    } catch (IOException e) {
      logger.error("Failed to retrieve data from the Quotes service:", e);
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/quotes")
  public ResponseEntity<String> createQuote(@RequestBody Data data) { 
    logger.info("Quote: " + data);

    // build a Quote
    Quote quote = new Quote();
    quote.setId(data.getId());
    quote.setAuthor(data.getAuthor());
    quote.setQuote(data.getQuote());

    ObjectMapper mapper = new ObjectMapper();
    String quoteString;
    try {
      quoteString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quote);

      ResponseBody quotes = makeAuthenticatedPostRequest(quotesURL, "quotes", quoteString);
      return new ResponseEntity<String>(quotes.string(), HttpStatus.OK);
    } catch (IOException e) {
      logger.error("Failed to post data to the Quotes service:" + e.getMessage());
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }    
  }

  @DeleteMapping("/quotes/{id}")
  public ResponseEntity<HttpStatus> deleteQuote(@PathVariable("id") Integer id) {
    logger.info("Delete by ID: " + id);

    String path = String.format("%s/%s", "quotes", id.toString());
    try {
      Response status = makeAuthenticatedDeleteRequest(quotesURL, path);
      return new ResponseEntity<HttpStatus>(HttpStatus.valueOf(status.code()));
    } catch (IOException e) {
      logger.error("Failed to delete data in the Quotes service:", e);
      return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseBody makeAuthenticatedRequest(String url, String path) throws IOException {
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

  public ResponseBody makeAuthenticatedPostRequest(String url, String path, String data) throws IOException {
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

  public Response makeAuthenticatedDeleteRequest(String url, String path) throws IOException {
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
