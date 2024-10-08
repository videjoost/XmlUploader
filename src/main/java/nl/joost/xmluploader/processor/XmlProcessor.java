package nl.joost.xmluploader.processor;

import org.w3c.dom.Node;

public interface XmlProcessor {

  void processXml(Node node) throws Exception;
}

