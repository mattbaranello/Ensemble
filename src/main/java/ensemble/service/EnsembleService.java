package ensemble.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ensemble.controller.model.ProjectData;
import ensemble.controller.model.ProjectData.ProjectSongwriter;
import ensemble.controller.model.ProjectData.ProjectSongwriter.ContributionData;
import ensemble.dao.ContributionDao;
import ensemble.dao.ProjectDao;
import ensemble.dao.SongwriterDao;
import ensemble.entity.Contribution;
import ensemble.entity.Project;
import ensemble.entity.Songwriter;

@Service
public class EnsembleService {
	
	//Instantiates the data access objects for each of the 3 entity classes
	@Autowired
	private ContributionDao contributionDao;
	
	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private SongwriterDao songwriterDao;
	
	/*
	 * Copies fields from the "ProjectData" object to the "Project". It's a way to copy data from one object to another so that
	 * fields can be updated.
	 */
	private void copyProjectFields(Project project, ProjectData projectData) {
		project.setProjectId(projectData.getProjectId());
		project.setCreationDate(projectData.getCreationDate());
		project.setName(projectData.getName());
		project.setBpm(projectData.getBpm());
		project.setGenre(projectData.getGenre());
	}
	
	/*
	 * This method saves the data from the ProjectData object into a Project object and 
	 * then returning a new ProjectData object containing the saved data.
	 */
	public ProjectData saveProject(ProjectData projectData) {
		Long projectId = projectData.getProjectId();
		Project project = findOrCreateProject(projectId);

		copyProjectFields(project, projectData);
		return new ProjectData(projectDao.save(project));
	}
	
	
	private Project findOrCreateProject(Long projectId) {
		Project project;
		
		if(Objects.isNull(projectId)) {
			project = new Project();
		}
		else {
			project = findProjectById(projectId);
		}
		return project;
	}
	
	private Project findProjectById(Long projectId) {
			return projectDao.findById(projectId)
					.orElseThrow(() -> new NoSuchElementException(
							"Project with ID=" + projectId + " was not found."));
	}
	
	//This method deletes the project by ID
	public void deleteProject(Long projectId) {
		Project project = findProjectById(projectId);
		projectDao.delete(project);
	}
	
	/*
	 * This method retrieves a project by entering the project ID.
	 * If the project ID is null, then it will return the below exception message.
	 * Otherwise, it will retrieve the Ensemble project.
	 */
	
	@Transactional(readOnly = true)
	public ProjectData retrieveEnsembleProjectById(Long projectId) {
		Project project = findProjectById(projectId);
		
		if(project == null) {
			throw new IllegalStateException("Ensemble project with ID=" + projectId + 
					" does not exist.");
		}
		
		return new ProjectData(project);
	}
	
	private void copySongwriterFields(Songwriter songwriter, ProjectSongwriter projectSongwriter) {
		songwriter.setSongwriterId(projectSongwriter.getSongwriterId());
		songwriter.setSongwriterUsername(projectSongwriter.getSongwriterUsername());
		songwriter.setSongwriterEmail(projectSongwriter.getSongwriterEmail());
	}
	
	@Transactional(readOnly = false)
	public ProjectSongwriter saveSongwriter(Long projectId, ProjectSongwriter projectSongwriter) {
		Project project = findProjectById(projectId);
		Long songwriterId = projectSongwriter.getSongwriterId();
		Songwriter songwriter = findOrAddSongwriterToProject(projectId, songwriterId);
		
		copySongwriterFields(songwriter, projectSongwriter);
		songwriter.setProject(project);
		project.getSongwriters().add(songwriter);
		Songwriter dbSongwriter = songwriterDao.save(songwriter);
		
		return new ProjectSongwriter(dbSongwriter);
		
	}
	
	@Transactional(readOnly = false)
	private Songwriter findOrAddSongwriterToProject(Long projectId, Long songwriterId) {
		Songwriter songwriter;
		
		if(Objects.isNull(songwriterId)) {
			songwriter = new Songwriter();
		}
		else {
			songwriter = findSongwriterById(songwriterId);
		}
		return songwriter;
	}
	
	
	private Songwriter findSongwriterById(Long songwriterId) {
		return songwriterDao.findById(songwriterId)
				.orElseThrow(() -> new NoSuchElementException(
						"Songwriter with ID=" + songwriterId + " was not found."));
	}

	private void copyContributionFields(Contribution contribution, ContributionData contributionData) {
		contribution.setContributionId(contributionData.getContributionId());
		contribution.setTimestamp(contributionData.getTimestamp());
	}
	
	public ContributionData saveSongwriterContribution(Long songwriterId, ContributionData contributionData) {
		Songwriter songwriter = findSongwriterById(songwriterId);
		Long contributionId = contributionData.getContributionId();
		Contribution contribution = findOrCreateContribution(contributionId);
		
		copyContributionFields(contribution, contributionData);
		contribution.getSongwriters().add(songwriter);
		songwriter.getContributions().add(contribution);
	    
		Contribution dbContribution = contributionDao.save(contribution);
		
		return new ContributionData(dbContribution);
	}

	private Contribution findOrCreateContribution(Long contributionId) {
		Contribution contribution;
		
		if(Objects.isNull(contributionId)) {
			contribution = new Contribution();
		}
		else {
			contribution = findContributionById(contributionId);
		}
		return contribution;
	}

	private Contribution findContributionById(Long contributionId) {
		Contribution contribution = contributionDao.findById(contributionId)
				.orElseThrow(() -> new NoSuchElementException("Contribution not found."));
		
		return contribution;
	}
}
	

