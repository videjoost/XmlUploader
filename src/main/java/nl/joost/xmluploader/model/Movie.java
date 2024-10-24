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
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "director", nullable = false)
  private String director;

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
    private String director;
    private String title;
    private String genre;
    private double price;
    private String releaseDate;
    private String description;

    public Builder director(String director) {
      this.director = director;
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

    public Movie build() {
      return new Movie(0, director, title, genre, price, releaseDate, description);
    }


  }
}
