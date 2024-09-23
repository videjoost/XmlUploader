package nl.joost.xmluploader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

  @GetMapping("/")
  public String index() {
    return "index";  // This looks for src/main/resources/templates/index.html
  }

  @GetMapping("/drop")
  public String showDropPage() {
    return "drop";
  }

}

