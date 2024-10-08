package nl.joost.xmluploader.processor.imp;

import nl.joost.xmluploader.model.Movie;
import nl.joost.xmluploader.repo.MovieRepo;
import nl.joost.xmluploader.processor.XmlProcessor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MovieXmlProcessor implements XmlProcessor {

  private final MovieRepo movieRepo;

  public MovieXmlProcessor(MovieRepo movieRepo) {
    this.movieRepo = movieRepo;
  }

  @Override
  public void processXml(Node node) throws Exception {
    Element element = (Element) node;
    Movie movie = new Movie();
    movie.setId(Integer.parseInt(element.getAttribute("id").substring(2)));
    movie.setDirector(element.getElementsByTagName("director").item(0).getTextContent());
    movie.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
    movie.setGenre(element.getElementsByTagName("genre").item(0).getTextContent());
    movie.setPrice(Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
    movie.setReleaseDate(element.getElementsByTagName("release_date").item(0).getTextContent());
    movie.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
    movieRepo.save(movie);
  }
}