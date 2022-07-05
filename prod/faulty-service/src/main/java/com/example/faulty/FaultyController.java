package com.example.faulty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.OutputKeys;

@RestController
public class FaultyController {

  // logger
  private static final Log logger = LogFactory.getLog(FaultyController.class);

  @Autowired
  private FaultyService faultyService;

  @GetMapping()
  public ResponseEntity<String> get(){
    return new ResponseEntity<>(faultyService.getDataFromFaultyBackingService(), HttpStatus.OK);
  }
}
