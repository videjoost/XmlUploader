package nl.joost.xmluploader.processor.imp;

import nl.joost.xmluploader.model.Music;
import nl.joost.xmluploader.repo.MusicRepo;
import nl.joost.xmluploader.processor.XmlProcessor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MusicXmlProcessor implements XmlProcessor {

  private final MusicRepo musicRepo;

  public MusicXmlProcessor(MusicRepo musicRepo) {
    this.musicRepo = musicRepo;
  }

  @Override
  public void processXml(Node node) throws Exception {
    try {
      Element element = (Element) node;
      Music album = new Music.Builder()
          .artist(element.getElementsByTagName("artist").item(0).getTextContent())
          .title(element.getElementsByTagName("title").item(0).getTextContent())
          .genre(element.getElementsByTagName("genre").item(0).getTextContent())
          .price(
          Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()))
          .releaseDate(element.getElementsByTagName("release_date").item(0).getTextContent())
          .description(element.getElementsByTagName("description").item(0).getTextContent())
          .build();
      musicRepo.save(album);
    } catch (Exception e) {
      throw new Exception("Error processing music XML", e);
    }
  }
}