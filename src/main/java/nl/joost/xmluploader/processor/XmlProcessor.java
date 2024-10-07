package nl.joost.xmluploader.processor;

import java.io.InputStream;

public interface XmlProcessor {

  void processXml(InputStream inputStream) throws Exception;
}

