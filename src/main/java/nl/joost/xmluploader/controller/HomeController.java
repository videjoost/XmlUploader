package nl.joost.xmluploader.controller;

import nl.joost.xmluploader.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class HomeController {

  @GetMapping("/")
  public String index() {
    return "index";  // This looks for src/main/resources/templates/index.html
  }


  @GetMapping("/signup")
  public String showSignupForm(Model model) {
    model.addAttribute("user", new User()); // Prepare a new User model for signup
    return "signup"; // Return a signup.html page for user registration
  }

}

