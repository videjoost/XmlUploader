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
    try {
    Element element = (Element) node;
    Movie movie = new Movie.Builder()
        .director(element.getElementsByTagName("director").item(0).getTextContent())
        .title(element.getElementsByTagName("title").item(0).getTextContent())
        .genre(element.getElementsByTagName("genre").item(0).getTextContent())
        .price(Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()))
        .releaseDate(element.getElementsByTagName("release_date").item(0).getTextContent())
        .description(element.getElementsByTagName("description").item(0).getTextContent())
        .build();
    movieRepo.save(movie);
  } catch (Exception e) {
    throw new Exception("Error processing movie XML", e);
  }
  }
}