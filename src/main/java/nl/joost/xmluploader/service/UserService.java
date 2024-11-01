package nl.joost.xmluploader.service;
import nl.joost.xmluploader.model.User;
import nl.joost.xmluploader.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


  private final UserRepo userRepository;


  public UserService(UserRepo userRepository) {
    this.userRepository = userRepository;
  }

  public User authenticate(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user != null && user.getPassword().equals(password)) {
      return user;
    }
    return null;
  }
}

