package nl.joost.xmluploader.service;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.model.Movie;
import nl.joost.xmluploader.model.Music;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.repo.MovieRepo;
import nl.joost.xmluploader.repo.MusicRepo;
import nl.joost.xmluploader.validator.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlProcessingService {

  private final XmlValidator xmlValidator;
  private final BookRepo bookRepo;
  private final MusicRepo musicRepo;
  private final MovieRepo movieRepo;

  @Autowired
  public XmlProcessingService(XmlValidator xmlValidator, BookRepo bookRepo, MusicRepo musicRepo, MovieRepo movieRepo) {
    this.xmlValidator = xmlValidator;
    this.bookRepo = bookRepo;
    this.musicRepo = musicRepo;
    this.movieRepo = movieRepo;
  }

  public void processXmlFile(MultipartFile file) throws Exception {
    if (!file.getContentType().equals("text/xml")) {
      throw new Exception("Invalid file type.");
    }

    try (InputStream inputStream = file.getInputStream()) {
      // Validate XML
      xmlValidator.validateXml(inputStream);

      // Reset inputStream to re-read the XML after validation
      inputStream.reset();

      // Parse and process the XML
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(inputStream);

      // Process the different types of entities
      processEntities(document);
    }
  }

  private void processEntities(Document document) throws Exception {
    // Process Albums (Music objects)
    NodeList albumList = document.getElementsByTagName("album");
    for (int i = 0; i < albumList.getLength(); i++) {
      Node albumNode = albumList.item(i);
      if (albumNode.getNodeType() == Node.ELEMENT_NODE) {
        processAlbum((Element) albumNode);
      }
    }

    // Process Books
    NodeList bookList = document.getElementsByTagName("book");
    for (int i = 0; i < bookList.getLength(); i++) {
      Node bookNode = bookList.item(i);
      if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
        processBook((Element) bookNode);
      }
    }

    // Process Movies
    NodeList movieList = document.getElementsByTagName("movie");
    for (int i = 0; i < movieList.getLength(); i++) {
      Node movieNode = movieList.item(i);
      if (movieNode.getNodeType() == Node.ELEMENT_NODE) {
        processMovie((Element) movieNode);
      }
    }
  }

  private void processAlbum(Element albumElement) {
    Music album = new Music();
    album.setId(Integer.parseInt(albumElement.getAttribute("id").substring(2)));  // Parsing id
    album.setArtist(albumElement.getElementsByTagName("artist").item(0).getTextContent());
    album.setTitle(albumElement.getElementsByTagName("title").item(0).getTextContent());
    album.setGenre(albumElement.getElementsByTagName("genre").item(0).getTextContent());
    album.setPrice(Double.parseDouble(albumElement.getElementsByTagName("price").item(0).getTextContent()));
    album.setReleaseDate(albumElement.getElementsByTagName("release_date").item(0).getTextContent());
    album.setDescription(albumElement.getElementsByTagName("description").item(0).getTextContent());

    // Save the album (music) object to the database
    musicRepo.save(album);
  }

  private void processBook(Element bookElement) {
    Book book = new Book();
    book.setId(Integer.parseInt(bookElement.getAttribute("id").substring(2)));  // Parsing id
    book.setAuthor(bookElement.getElementsByTagName("author").item(0).getTextContent());
    book.setTitle(bookElement.getElementsByTagName("title").item(0).getTextContent());
    book.setGenre(bookElement.getElementsByTagName("genre").item(0).getTextContent());
    book.setPrice(Double.parseDouble(bookElement.getElementsByTagName("price").item(0).getTextContent()));
    book.setPublishDate(bookElement.getElementsByTagName("publish_date").item(0).getTextContent());
    book.setDescription(bookElement.getElementsByTagName("description").item(0).getTextContent());

    // Save the book object to the database
    bookRepo.save(book);
  }

  private void processMovie(Element movieElement) {
    Movie movie = new Movie();
    movie.setId(Integer.parseInt(movieElement.getAttribute("id").substring(2)));  // Parsing id
    movie.setDirector(movieElement.getElementsByTagName("director").item(0).getTextContent());
    movie.setTitle(movieElement.getElementsByTagName("title").item(0).getTextContent());
    movie.setGenre(movieElement.getElementsByTagName("genre").item(0).getTextContent());
    movie.setPrice(Double.parseDouble(movieElement.getElementsByTagName("price").item(0).getTextContent()));
    movie.setReleaseDate(movieElement.getElementsByTagName("release_date").item(0).getTextContent());
    movie.setDescription(movieElement.getElementsByTagName("description").item(0).getTextContent());

    // Save the movie object to the database
    movieRepo.save(movie);
  }
}
