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
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  @Id
  @Column(name = "id")
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "author", nullable = false)
  private String author;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "genre", nullable = false)
  private String genre;

  @Column(name = "price", nullable = false)
  private double price;

  @Column(name = "publish_date", nullable = false)
  private String publishDate;

  @Column(name = "description", nullable = false)
  private String description;

  public static class Builder {
    private String author;
    private String title;
    private String genre;
    private double price;
    private String publishDate;
    private String description;

    public Builder author(String author) {
      this.author = author;
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

    public Builder publishDate(String publishDate) {
      this.publishDate = publishDate;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Book build() {
      return new Book(0, author, title, genre, price, publishDate, description);
    }
  }
}
