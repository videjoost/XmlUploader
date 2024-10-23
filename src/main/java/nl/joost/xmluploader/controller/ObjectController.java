package nl.joost.xmluploader.controller;

import java.util.List;
import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.model.Movie;
import nl.joost.xmluploader.model.Music;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.repo.MovieRepo;
import nl.joost.xmluploader.repo.MusicRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ObjectController {


  private final BookRepo bookrepo;
  private final MusicRepo musicrepo;
  private final MovieRepo movierepo;

  public ObjectController(BookRepo bookrepo, MusicRepo musicrepo, MovieRepo movierepo) {
    this.bookrepo = bookrepo;
    this.musicrepo = musicrepo;
    this.movierepo = movierepo;
  }

  @GetMapping("/objects")
  public String viewImportedXmls(@RequestParam(name = "type", required = false) String type,
      Model model) {
    if (type == null || type.equals("all")) {
      List<Book> books = bookrepo.findAll();
      List<Music> music = musicrepo.findAll();
      List<Movie> movies = movierepo.findAll();

      model.addAttribute("books", books);
      model.addAttribute("music", music);
      model.addAttribute("movies", movies);
    } else {
      switch (type) {
        case "book":
          model.addAttribute("books", bookrepo.findAll());
          break;

        case "music":
          model.addAttribute("music", musicrepo.findAll());
          break;
        case "movie":
          model.addAttribute("movies", movierepo.findAll());
          break;
        default:
          throw new IllegalArgumentException("Invalid type: " + type);
      }
    }

    model.addAttribute("selectedType", type != null ? type : "all");

    return "objects";
  }


}
