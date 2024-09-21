package nl.joost.xmluploader.controller;

import lombok.extern.slf4j.Slf4j;
import nl.joost.xmluploader.service.XmlProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
  public ResponseEntity<String> uploadXml(@RequestParam("file") MultipartFile file) {
    final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final UserDetails user = (UserDetails) principal;

    log.info("User {} is uploading a file", user.getUsername());

    try {
      xmlProcessingService.processXmlFile(file);
      return ResponseEntity.status(HttpStatus.OK).body("File uploaded and processed successfully.");
    } catch (Exception e) {
      log.error("Error processing file: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid XML file.");
    }
  }
}