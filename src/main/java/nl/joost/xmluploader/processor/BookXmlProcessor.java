package nl.joost.xmluploader.processor;

import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.processor.XmlProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;

import org.xml.sax.SAXException;
import org.springframework.stereotype.Component;

@Component
public class BookXmlProcessor implements XmlProcessor {

  private final BookRepo bookRepo;

  public BookXmlProcessor(BookRepo bookRepo) {
    this.bookRepo = bookRepo;
  }

  @Override
  public void processXml(InputStream inputStream) throws Exception {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(inputStream);

      NodeList nodeList = document.getElementsByTagName("book");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          Book book = buildBookFromElement(element); // Extracting book building logic to a separate method
          bookRepo.save(book);
        }
      }
    } catch (ParserConfigurationException | SAXException e) {
      throw new Exception("Error parsing the XML file: " + e.getMessage());
    }
  }


  private Book buildBookFromElement(Element element) throws Exception {
    Book book = new Book();

    String id = element.getAttribute("id");

    if (id.startsWith("bk")) {

      String numericPart = id.substring(2); // Removes the "bk" prefix
      book.setId(Integer.parseInt(numericPart));
    } else {

      throw new Exception("Invalid book ID format: " + id);
    }

    book.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
    book.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
    book.setGenre(element.getElementsByTagName("genre").item(0).getTextContent());
    book.setPrice(Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
    book.setPublishDate(element.getElementsByTagName("publish_date").item(0).getTextContent());
    book.setDescription(element.getElementsByTagName("description").item(0).getTextContent());

    return book;
  }
}
