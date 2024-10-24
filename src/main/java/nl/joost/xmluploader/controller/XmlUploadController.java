package nl.joost.xmluploader.controller;

import lombok.extern.slf4j.Slf4j;
import nl.joost.xmluploader.service.XmlProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/private/xml")
public class XmlUploadController {

  private final XmlProcessingService xmlProcessingService;

  @Autowired
  public XmlUploadController(XmlProcessingService xmlProcessingService) {
    this.xmlProcessingService = xmlProcessingService;
  }

  @PostMapping(value = "/upload", produces = {MediaType.TEXT_PLAIN_VALUE})
  @ResponseBody
  public ResponseEntity<String> uploadXml(@RequestParam("file") MultipartFile file) {
    log.info("File upload initiated: {} bytes", file.getSize());

    try {
      xmlProcessingService.processXmlFile(file);
      return new ResponseEntity<>("File uploaded and processed successfully.", HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error processing file: {}", e.getMessage(), e);
      return new ResponseEntity<>("Error processing file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
