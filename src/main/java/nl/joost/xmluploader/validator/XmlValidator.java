package nl.joost.xmluploader.validator;

import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class XmlValidator {

  public void validateXml(InputStream xml, String xmlType) throws Exception {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    StreamSource schemaFile;

    switch (xmlType) {
      case "book":
        schemaFile = new StreamSource(getClass().getResourceAsStream("/book.xsd"));
        break;
      case "music":
        schemaFile = new StreamSource(getClass().getResourceAsStream("/music.xsd"));
        break;
      case "movie":
        schemaFile = new StreamSource(getClass().getResourceAsStream("/movie.xsd"));
        break;
      default:
        throw new UnsupportedOperationException("Unsupported XML type: " + xmlType);
    }

    javax.xml.validation.Validator validator = factory.newSchema(schemaFile).newValidator();

    validator.setErrorHandler(new DefaultHandler() {
      @Override
      public void error(SAXParseException e) throws SAXException {
        throw new SAXException("Error at line " + e.getLineNumber() + ", column " + e.getColumnNumber() + ": " + e.getMessage());
      }

      @Override
      public void fatalError(SAXParseException e) throws SAXException {
        throw new SAXException("Fatal error at line " + e.getLineNumber() + ", column " + e.getColumnNumber() + ": " + e.getMessage());
      }

      @Override
      public void warning(SAXParseException e) throws SAXException {
        throw new SAXException("Warning at line " + e.getLineNumber() + ", column " + e.getColumnNumber() + ": " + e.getMessage());
      }
    });

    try {
      validator.validate(new StreamSource(xml));
    } catch (SAXException e) {
      throw new Exception("Invalid XML: " + e.getMessage());
    }
  }
}