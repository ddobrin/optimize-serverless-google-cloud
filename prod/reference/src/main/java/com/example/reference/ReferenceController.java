package com.example.reference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.example.reference.data.Metadata;
import com.google.cloud.MetadataConfig;

import java.util.concurrent.TimeUnit;

@RestController
public class ReferenceController {
  @Value("${delay}")
  Long delay;

  // logger
  private static final Log logger = LogFactory.getLog(ReferenceController.class);

  @GetMapping("/metadata") 
  public ResponseEntity<Metadata> metadata(){
    Metadata data = new Metadata();
    data.setProjectID(MetadataConfig.getProjectId());
    data.setZone(MetadataConfig.getZone());
    data.setInstanceID(MetadataConfig.getInstanceId());

    // introduce an artificial delay
    try {
      TimeUnit.SECONDS.sleep(delay);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }

    return new ResponseEntity<Metadata>(data, HttpStatus.OK);
  }
}
