package nl.joost.xmluploader.util;


import nl.joost.xmluploader.processor.XmlProcessor;
import nl.joost.xmluploader.processor.imp.BookXmlProcessor;
import nl.joost.xmluploader.processor.imp.MovieXmlProcessor;
import nl.joost.xmluploader.processor.imp.MusicXmlProcessor;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.repo.MovieRepo;
import nl.joost.xmluploader.repo.MusicRepo;

public class XmlProcessorFactory {

  public static XmlProcessor getProcessor(String xmlType, BookRepo bookRepo, MusicRepo musicRepo, MovieRepo movieRepo) {
    if ("book".equals(xmlType)) {
      return new BookXmlProcessor(bookRepo);
    } else if ("music".equals(xmlType)) {
      return new MusicXmlProcessor(musicRepo);
    } else if ("movie".equals(xmlType)) {
      return new MovieXmlProcessor(movieRepo);
    }
    throw new UnsupportedOperationException("Unsupported XML type: " + xmlType);
  }
}
