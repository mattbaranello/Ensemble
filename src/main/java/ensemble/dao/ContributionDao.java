package ensemble.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ensemble.entity.Contribution;

public interface ContributionDao extends JpaRepository<Contribution, Long> {


}
