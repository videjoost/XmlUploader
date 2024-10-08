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
}
