package nl.joost.xmluploader.validator;

import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class XmlValidator {

  public void validateXml(InputStream xmlInputStream, String xmlType) throws Exception {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    StreamSource schemaFile = null;

    switch (xmlType.toLowerCase()) {
      case "book":
        schemaFile = new StreamSource(getClass().getResourceAsStream("/schemas/book.xsd"));
        break;
      case "music":
        schemaFile = new StreamSource(getClass().getResourceAsStream("/schemas/music.xsd"));
        break;
      case "movie":
        schemaFile = new StreamSource(getClass().getResourceAsStream("/schemas/movie.xsd"));
        break;
      default:
        schemaFile = new StreamSource(getClass().getResourceAsStream("/schemas/catalog.xsd"));
        break;
    }

    if (schemaFile.getInputStream() == null) {
      throw new Exception("XSD file not found for type: " + xmlType);
    }

    // Load the schema and validate the XML against it
    Schema schema = factory.newSchema(schemaFile);
    Validator validator = schema.newValidator();

    // Error handling
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

    // Validate the XML
    try {
      validator.validate(new StreamSource(xmlInputStream));
    } catch (SAXException e) {
      throw new Exception("Invalid XML: " + e.getMessage(), e);
    }
  }
}
