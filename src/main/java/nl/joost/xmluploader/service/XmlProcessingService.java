package nl.joost.xmluploader.service;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.validator.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

@Service
public class XmlProcessingService {


  private final XmlValidator xmlValidator;
  private final BookRepo bookRepo;

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
      xmlValidator.validateXml(inputstream);
    } catch (Exception e) {
      throw new Exception("XML structure is invalid: " + e.getMessage());
    }

    try (InputStream inputstream = file.getInputStream()) {
      parseAndSaveBooks(inputstream);
    }
  }

  private void parseAndSaveBooks(InputStream inputStream) throws Exception {
    try {
      // parse
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(inputStream);

      NodeList nodeList = document.getElementsByTagName("book");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          Book book = new Book();
          book.setId(Integer.parseInt(element.getAttribute("id").substring(2)));
          book.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
          book.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
          book.setGenre(element.getElementsByTagName("genre").item(0).getTextContent());
          book.setPrice(
              Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
          book.setPublishDate(
              element.getElementsByTagName("publish_date").item(0).getTextContent());
          book.setDescription(element.getElementsByTagName("description").item(0).getTextContent());

          bookRepo.save(book);
        }
      }
    } catch (ParserConfigurationException | SAXException e) {
      throw new Exception("Error parsing the XML file: " + e.getMessage());
    }
  }
}





