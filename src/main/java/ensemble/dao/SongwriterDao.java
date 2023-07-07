package ensemble.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ensemble.entity.Songwriter;

public interface SongwriterDao extends JpaRepository<Songwriter, Long> {

}
