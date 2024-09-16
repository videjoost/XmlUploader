package nl.joost.xmluploader.validator;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class XmlValidator {

  private Schema schema;

  public XmlValidator() throws SAXException {
    // Load the XSD schema file for validation
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    InputStream xsdStream = getClass().getResourceAsStream("/schema/book.xsd");  // Ensure correct path
    schema = factory.newSchema(new StreamSource(xsdStream));
  }

  /**
   * Validates the XML file against the XSD schema.
   *
   * @param xmlFile the XML file to validate
   * @throws SAXException if the XML is not valid
   * @throws IOException if there's an issue reading the file
   */
  public void validate(File xmlFile) throws SAXException, IOException {
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(xmlFile));
  }
}
