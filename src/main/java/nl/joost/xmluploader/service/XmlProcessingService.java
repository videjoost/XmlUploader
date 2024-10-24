package nl.joost.xmluploader.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import nl.joost.xmluploader.processor.XmlProcessor;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.repo.MovieRepo;
import nl.joost.xmluploader.repo.MusicRepo;
import nl.joost.xmluploader.util.XmlProcessorFactory;
import nl.joost.xmluploader.validator.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;

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
    if (!"text/xml".equals(file.getContentType()) && !"application/xml".equals(file.getContentType())) {
      throw new Exception("Invalid file type.");
    }

    String xmlType = detectXmlType(file);
    if ("unknown".equals(xmlType)) {
      throw new Exception("Unsupported XML type detected.");
    }

    // Read the file's InputStream into a byte array so we can use it multiple times
    byte[] xmlBytes = file.getBytes();

    // Validate XML
    try (InputStream inputStream = new ByteArrayInputStream(xmlBytes)) {
      xmlValidator.validateXml(inputStream, xmlType);
    }

    // Process the XML
    try (InputStream inputStream = new ByteArrayInputStream(xmlBytes)) {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(inputStream);
      processEntities(document, xmlType);
    }
  }


  private void processEntities(Document document, String xmlType) throws Exception {
    String tagName = switch (xmlType.toLowerCase()) {
      case "book" -> "book";
      case "music" -> "album";
      case "movie" -> "movie";
      default -> throw new UnsupportedOperationException("Unsupported XML type: " + xmlType);
    };

    XmlProcessor processor = XmlProcessorFactory.getProcessor(xmlType, bookRepo, musicRepo, movieRepo);
    NodeList nodeList = document.getElementsByTagName(tagName);
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        processor.processXml(node);
      }
    }
  }

  private String detectXmlType(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    if (fileName != null) {
      if (fileName.contains("book")) {
        return "book";
      } else if (fileName.contains("music")) {
        return "music";
      } else if (fileName.contains("movie")) {
        return "movie";
      } else if (fileName.contains("catalog")) {
        return "catalog";  // Handle mixed types
      }
    }
    return "unknown";
  }

}
