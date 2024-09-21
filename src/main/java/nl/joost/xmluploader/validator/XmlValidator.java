package nl.joost.xmluploader.validator;

import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.springframework.stereotype.Component;
import javax.xml.validation.Schema;


@Component
public class XmlValidator {

  public void validateXml(InputStream xml) throws Exception {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    StreamSource schemaFile = new StreamSource(getClass().getResourceAsStream("/schema.xsd"));
    javax.xml.validation.Validator validator = factory.newSchema(schemaFile).newValidator();
    validator.validate(new StreamSource(xml));
  }

  private Schema schema;

}
