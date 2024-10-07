package nl.joost.xmluploader.service;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.processor.XmlProcessor;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.util.XmlProcessorFactory;
import nl.joost.xmluploader.validator.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

@Service
public class XmlProcessingService {

  private final XmlValidator xmlValidator;
  private final BookRepo bookRepo; // Still needed for processor injection

  @Autowired
  public XmlProcessingService(XmlValidator xmlValidator, BookRepo bookRepo) {
    this.xmlValidator = xmlValidator;
    this.bookRepo = bookRepo;
  }

  public void processXmlFile(MultipartFile file) throws Exception {
    if (!file.getContentType().equals("text/xml")) {
      throw new Exception("Invalid file type.");
    }

    try (InputStream inputstream = file.getInputStream()) {
      xmlValidator.validateXml(inputstream); // Keep the validation
    }

    try (InputStream inputstream = file.getInputStream()) {
      String xmlType = detectXmlType(file); // Implement logic to detect XML type, e.g., "book"
      XmlProcessor processor = XmlProcessorFactory.getProcessor(xmlType, bookRepo);
      processor.processXml(inputstream);
    }
  }

  private String detectXmlType(MultipartFile file) {
    // Implement logic to detect the XML type (can be based on the root tag or metadata)
    return "book"; // Example: this would return "book" for book XMLs
  }
}






