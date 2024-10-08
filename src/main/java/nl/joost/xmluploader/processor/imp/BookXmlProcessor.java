package nl.joost.xmluploader.processor.imp;

import nl.joost.xmluploader.model.Book;
import nl.joost.xmluploader.repo.BookRepo;
import nl.joost.xmluploader.processor.XmlProcessor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class BookXmlProcessor implements XmlProcessor {

  private final BookRepo bookRepo;

  public BookXmlProcessor(BookRepo bookRepo) {
    this.bookRepo = bookRepo;
  }

  @Override
  public void processXml(Node node) throws Exception {
    try {
      Element element = (Element) node;
      Book book = new Book();
      book.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
      book.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
      book.setGenre(element.getElementsByTagName("genre").item(0).getTextContent());
      book.setPrice(
          Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
      book.setPublishDate(element.getElementsByTagName("publish_date").item(0).getTextContent());
      book.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
      bookRepo.save(book);
    } catch (Exception e) {
      throw new Exception("Error processing book XML", e);
    }
  }
}