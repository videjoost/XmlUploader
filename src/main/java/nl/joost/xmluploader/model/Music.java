package nl.joost.xmluploader.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "album")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Music {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "artist", nullable = false)
  private String artist;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "genre", nullable = false)
  private String genre;

  @Column(name = "price", nullable = false)
  private double price;

  @Column(name = "release_date", nullable = false)
  private String releaseDate;

  @Column(name = "description", nullable = false)
  private String description;



  public static class Builder {
    private String artist;
    private String title;
    private String genre;
    private double price;
    private String releaseDate;
    private String description;

    public Builder artist(String artist) {
      this.artist = artist;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder genre(String genre) {
      this.genre = genre;
      return this;
    }

    public Builder price(double price) {
      this.price = price;
      return this;
    }

    public Builder releaseDate(String releaseDate) {
      this.releaseDate = releaseDate;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Music build() {
      return new Music(0, artist, title, genre, price, releaseDate, description);
    }
  }
}
