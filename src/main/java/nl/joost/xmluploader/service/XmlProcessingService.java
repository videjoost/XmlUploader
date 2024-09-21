package nl.joost.xmluploader.service;

import java.io.InputStream;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import nl.joost.xmluploader.validator.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlProcessingService {


  private final XmlValidator xmlValidator;
  public XmlProcessingService(XmlValidator xmlValidator) {
    this.xmlValidator = xmlValidator;
  }

  public void processXmlFile(MultipartFile file) throws Exception {
    if (!Objects.equals(file.getContentType(), "application/xml")) {
      throw new Exception("Invalid file type.");
    }
    InputStream inputStream = file.getInputStream();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    builder.parse(inputStream);

    xmlValidator.validateXml(inputStream);
  }



}
