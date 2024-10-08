package nl.joost.xmluploader.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
}
