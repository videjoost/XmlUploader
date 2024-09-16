package nl.joost.xmluploader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index() {
    return "index";  // This looks for src/main/resources/templates/index.html
  }

  @GetMapping("/login")
  public String login() {
    return "login";  // This looks for src/main/resources/templates/login.html
  }
}

