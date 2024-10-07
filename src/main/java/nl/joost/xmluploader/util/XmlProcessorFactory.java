package nl.joost.xmluploader.util;

import nl.joost.xmluploader.processor.XmlProcessor;
import nl.joost.xmluploader.processor.BookXmlProcessor;
import nl.joost.xmluploader.repo.BookRepo;

public class XmlProcessorFactory {

  public static XmlProcessor getProcessor(String xmlType, BookRepo bookRepo) {
    if ("book".equals(xmlType)) {
      return new BookXmlProcessor(bookRepo);
    }
    // You can add more processors for different XML types here
    throw new UnsupportedOperationException("Unsupported XML type: " + xmlType);
  }
}
