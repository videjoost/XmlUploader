package nl.joost.xmluploader.controller;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;
import nl.joost.xmluploader.service.XmlProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
// @Secured({"ROLE_USER"})
@RequestMapping("/private/xml")
@Controller
public class XmlUploadController {

  private final XmlProcessingService xmlProcessingService;

  @Autowired
  public XmlUploadController(XmlProcessingService xmlProcessingService) {
    this.xmlProcessingService = xmlProcessingService;
  }

  @PostMapping(value = "/upload", produces = {MediaType.TEXT_PLAIN_VALUE})
  @ResponseBody
  public ResponseEntity<String> uploadXml(@RequestParam("file") MultipartFile file, @RequestParam("type") String xmlType) {
    log.info("File upload initiated");
    try {
      xmlProcessingService.processXmlFile(file.getInputStream(), xmlType);
      return new ResponseEntity<>("File uploaded and processed successfully.", HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error processing file: {}", e.getMessage(), e);
      return new ResponseEntity<>("Error processing file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);}
  }

  @GetMapping("/success")
  public String success(Model model) {
    return "redirect:/books";
  }
}