package ensemble.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ensemble.entity.Project;

public interface ProjectDao extends JpaRepository<Project, Long> {


}
