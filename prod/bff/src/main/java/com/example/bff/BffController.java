package com.example.bff;

import com.example.bff.actuator.StartupCheck;
import com.example.data.Data;
import com.example.data.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class BffController {
  // logger
  private static final Log logger = LogFactory.getLog(BffController.class);

  @Value("${quotes_url}")
  String quotesURL;

  @Value("${reference_url}")
  String referenceURL;

  @Value("${faulty_url}")
  String faultyUrl;

  static long read;
  static long write;

  @Value("${read_timeout}")
  public void setRead(Long readTimeout) {
    read = readTimeout;
  }

  @Value("${write_timeout}")
  public void setWrite(Long writeTimeout){
    write = writeTimeout;
  }

  // Instantiate OkHttpClient
  private static OkHttpClient ok = null;

  // Metadata
  private static String metadata = null;

  @PostConstruct
  public void init() {
      logger.info("BffController: Post Construct Initializer");
      if(referenceURL==null){
        logger.error("Reference Service URL has not been configured. Please set the QUOTES_URL env variable");
        StartupCheck.down();
      }

      if(quotesURL==null){
        logger.error("Quotes Service URL has not been configured. Please set the QUOTES_URL env variable");
        StartupCheck.down();
      }

      // build the HTTP client
      ok = new OkHttpClient.Builder()
            .readTimeout(read, TimeUnit.MILLISECONDS)
            .writeTimeout(write, TimeUnit.MILLISECONDS)
            .build();

      // retrieve all quotes
      try {
        ResponseBody data = ServiceRequests.makeAuthenticatedRequest(ok, referenceURL, "metadata");
        metadata = data.string();

        logger.info("Metadata:" + metadata);
      } catch (IOException e) {
        logger.error("Unable to get Reference service data", e);

        // fail the startup actuator
        StartupCheck.down();
        return;
      }

      // enable the startup actuator
      StartupCheck.up();
  }

  @GetMapping("/quotes") 
  public ResponseEntity<String> allQuotes(){
    // retrieve all quotes
    try {
      ResponseBody quotes = ServiceRequests.makeAuthenticatedRequest(ok, quotesURL, "quotes");

      return new ResponseEntity<String>(quotes.string(), HttpStatus.OK);
    } catch (IOException e) {
      logger.error("Failed to retrieve data from the Quotes service:", e);
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/faulty")
  public ResponseEntity<String> getFromFaultyService() {
    // retrieve all quotes
    try {
      ResponseBody faultyResponse = ServiceRequests.makeAuthenticatedRequest(ok, faultyUrl, "");

      return new ResponseEntity<String>(faultyResponse.string(), HttpStatus.OK);
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

      ResponseBody quotes = ServiceRequests.makeAuthenticatedPostRequest(ok, quotesURL, "quotes", quoteString);
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
      Response status = ServiceRequests.makeAuthenticatedDeleteRequest(ok, quotesURL, path);
      return new ResponseEntity<HttpStatus>(HttpStatus.valueOf(status.code()));
    } catch (IOException e) {
      logger.error("Failed to delete data in the Quotes service:", e);
      return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
