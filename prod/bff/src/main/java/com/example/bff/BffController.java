package com.example.bff;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.data.Data;
import com.example.data.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.Response;
import okhttp3.ResponseBody;

@RestController
public class BffController {
  // logger
  private static final Log logger = LogFactory.getLog(BffController.class);

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
        ResponseBody metadata = ServiceRequests.makeAuthenticatedRequest(referenceURL, "metadata");

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
      ResponseBody quotes = ServiceRequests.makeAuthenticatedRequest(quotesURL, "quotes");

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

      ResponseBody quotes = ServiceRequests.makeAuthenticatedPostRequest(quotesURL, "quotes", quoteString);
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
      Response status = ServiceRequests.makeAuthenticatedDeleteRequest(quotesURL, path);
      return new ResponseEntity<HttpStatus>(HttpStatus.valueOf(status.code()));
    } catch (IOException e) {
      logger.error("Failed to delete data in the Quotes service:", e);
      return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
