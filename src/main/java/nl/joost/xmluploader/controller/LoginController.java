package nl.joost.xmluploader.controller;
import nl.joost.xmluploader.model.User;
import nl.joost.xmluploader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("user", new User());
    return "login"; // This returns the login.html Thymeleaf template
  }

  @PostMapping("/login")
  public String processLogin(User user, Model model) {
    User loggedInUser = userService.authenticate(user.getUsername(), user.getPassword());
    if (loggedInUser != null) {
      model.addAttribute("user", loggedInUser);
      return "dashboard"; // Redirect to a dashboard or home page
    } else {
      model.addAttribute("loginError", true);
      return "login"; // Reload login page on failure
    }
  }
}

