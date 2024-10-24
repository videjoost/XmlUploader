package nl.joost.xmluploader.controller;

import lombok.extern.slf4j.Slf4j;
import nl.joost.xmluploader.service.XmlProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping("/upload")
  public String uploadXml(@RequestParam("file") MultipartFile file, Model model) {
    log.info("File upload initiated: {} bytes", file.getSize());

    if (file.getSize() == 0) {
      model.addAttribute("errorMessage", "Uploaded file is empty.");
      return "redirect:/drop";
    }

    try {
      xmlProcessingService.processXmlFile(file);
      model.addAttribute("successMessage", "File uploaded and processed successfully.");
      return "redirect:/objects";

    } catch (Exception e) {
      log.error("Error processing file: {}", e.getMessage(), e);
      model.addAttribute("errorMessage", "Error processing file: " + e.getMessage());
      return "redirect:/drop";
    }
  }

  @GetMapping("/success")
  public String success(Model model) {
    return "redirect:/objects";
  }
}
