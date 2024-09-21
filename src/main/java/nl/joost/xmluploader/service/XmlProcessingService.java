package nl.joost.xmluploader.service;

import java.io.InputStream;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.validator.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;

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
    if (!Objects.equals(file.getContentType(), "application/xml")) {
      throw new Exception("Invalid file type.");
    }
    InputStream inputStream = file.getInputStream();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(inputStream);

    xmlValidator.validateXml(inputStream);

    NodeList nodeList = document.getElementsByTagName("book");
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        Book book = new Book();
        book.setId(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()));
        book.setAuthor(element.getElementsByTagName("title").item(0).getTextContent());
        book.setTitle(element.getElementsByTagName("author").item(0).getTextContent());
        book.setGenre(element.getElementsByTagName("genre").item(0).getTextContent());
        book.setPrice(
            Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
        book.setPublishDate(element.getElementsByTagName("publish_date").item(0).getTextContent());
        book.setDescription(element.getElementsByTagName("description").item(0).getTextContent());

        bookRepo.save(book);
      } else {
        System.out.println("Warning: object" + node.getNodeName() + "found");
      }
    }
  }



}
