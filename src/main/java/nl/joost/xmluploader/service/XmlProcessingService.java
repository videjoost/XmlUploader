package nl.joost.xmluploader.service;

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
    if (!file.getContentType().equals("text/xml")) {
      throw new Exception("Invalid file type.");
    }

    String xmlType = detectXmlType(file);

    try (InputStream inputStream = file.getInputStream()) {
      // Validate and process the XML
      processXmlFile(inputStream, xmlType);
    }
  }

  public void processXmlFile(InputStream xmlInputStream, String xmlType) throws Exception {
    // Validate the XML
    xmlValidator.validateXml(xmlInputStream, xmlType);

    // Reset the input stream to read it again
    xmlInputStream.reset();

    // Parse the XML document
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(xmlInputStream);

    // Process the entities based on the xmlType
    processEntities(document, xmlType);
  }

  private void processEntities(Document document, String xmlType) throws Exception {
    String tagName;
    switch (xmlType.toLowerCase()) {
      case "book":
        tagName = "book";
        break;
      case "music":
        tagName = "album";
        break;
      case "movie":
        tagName = "movie";
        break;
      default:
        throw new UnsupportedOperationException("Unsupported XML type: " + xmlType);
    }

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
    if (file.getOriginalFilename().contains("book")) {
      return "book";
    } else if (file.getOriginalFilename().contains("music")) {
      return "music";
    } else if (file.getOriginalFilename().contains("movie")) {
      return "movie";
    }
    return "unknown";
  }
}