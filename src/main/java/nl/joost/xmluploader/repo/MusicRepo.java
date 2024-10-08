package nl.joost.xmluploader.repo;

import nl.joost.xmluploader.model.Movie;
import nl.joost.xmluploader.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepo extends JpaRepository<Music, Integer> {

}
