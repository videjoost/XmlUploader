package nl.joost.xmluploader.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.joost.xmluploader.model.User;
import nl.joost.xmluploader.repo.UserRepo;
import nl.joost.xmluploader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

  @Autowired
  private UserRepo userRepo;

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
      return "menu"; // Redirect to a dashboard or home page
    } else {
      model.addAttribute("errorMessage", "Invalid username or password.");
      return "login"; // Reload login page on failure
    }
  }
  // Handle Signup Form (GET request)
  @GetMapping("/signup")
  public String showSignupForm(Model model) {
    model.addAttribute("user", new User());
    return "signup";
  }

  // Handle Save User (POST request)
  @PostMapping("/signup")
  public String saveUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("errorMessage", "Something went wrong please try again");
      return "signup";
    }

    if (userRepo.existsByUsername(user.getUsername())) {
      model.addAttribute("errorMessage", "Username is already taken.");
      return "signup";
    }
    if (userRepo.existsByEmail(user.getEmail())) {
      model.addAttribute("errorMessage", "Email is already in use.");
      return "signup";
    }


    userRepo.save(user);
    return "redirect:/login";
  }

  // Handle Update User (GET request)
  @GetMapping("/user/{id}/edit")
  public String showEditForm(@PathVariable("id") int id, Model model) {
    User user = userRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
    model.addAttribute("user", user);
    return "update-user";
  }

  // Handle Update User (POST request)
  @PostMapping("/user/{id}/update")
  public String updateUser(@PathVariable("id") int id, @ModelAttribute("user") User user, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "update-user";
    }
    user.setId(id);
    userRepo.save(user);
    return "redirect:/user/" + id + "/edit?updated";
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login";
  }
}
