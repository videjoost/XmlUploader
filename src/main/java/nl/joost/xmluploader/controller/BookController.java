package nl.joost.xmluploader.controller;

import org.springframework.ui.Model;
import java.util.List;
import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

  private final BookRepo bookrepo;

  @Autowired
  public BookController(BookRepo bookrepo) {
    this.bookrepo = bookrepo;
  }

  @GetMapping("/books")
  public String listBooks(Model model) {
    List<Book> books = bookrepo.findAll();
    model.addAttribute("books", books);
    return "objects";
  }

}
