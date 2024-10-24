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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/private/xml")
public class XmlUploadController {

  private final XmlProcessingService xmlProcessingService;

  @Autowired
  public XmlUploadController(XmlProcessingService xmlProcessingService) {
    this.xmlProcessingService = xmlProcessingService;
  }

  @PostMapping(value = "/upload")
  public String uploadXml(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) {
    try {
      // Call the service to process the uploaded file
      xmlProcessingService.processXmlFile(file);
      // Add a success message to be shown on the drop page
      redirectAttributes.addFlashAttribute("message", "Upload successful!");
    } catch (Exception e) {
      // Add an error message to be shown on the drop page
      redirectAttributes.addFlashAttribute("message", "Error processing file: " + e.getMessage());
    }
    // Redirect back to the "drop" page
    return "redirect:/drop";
  }



}
