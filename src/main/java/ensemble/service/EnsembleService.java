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
	
	//Finds project by ID. If project can't be found, it will throw an exception message.
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
	
	/*
	 * Copies fields from the "ProjectSongwriter" object to the "Songwriter". It's a way to copy data from one object to another so that
	 * fields can be updated.
	 */
	private void copySongwriterFields(Songwriter songwriter, ProjectSongwriter projectSongwriter) {
		songwriter.setSongwriterId(projectSongwriter.getSongwriterId());
		songwriter.setSongwriterUsername(projectSongwriter.getSongwriterUsername());
		songwriter.setSongwriterEmail(projectSongwriter.getSongwriterEmail());
	}
	
	/*
	 * This method saves the data from the ProjectSongwriter object into a Songwriter object and 
	 * then returning a new ProjectSongwriter object containing the saved data.
	 */
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
	
	/*
	 * This method finds a songwriter by ID. If the songwriter ID is null, it will create an new songwriter object. 
	 */
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
	//Finds songwriter by ID. If the songwriter does not exist, it will throw an exception message
	private Songwriter findSongwriterById(Long songwriterId) {
		return songwriterDao.findById(songwriterId)
				.orElseThrow(() -> new NoSuchElementException(
						"Songwriter with ID=" + songwriterId + " was not found."));
	}
	
	/*
	 * This method retrieves a contribution by ID. If there is no contribution with the ID that the user inputs, 
	 * it will return the exception message below in the "findContributionById" method.
	 */
	@Transactional(readOnly = true)
	public ContributionData retrieveContributionById(Long contributionId) {
		
			Contribution contribution = findContributionById(contributionId);
			return new ContributionData(contribution);
	}
	
	private Contribution findContributionById(Long contributionId) {
		return contributionDao.findById(contributionId)
				.orElseThrow(() -> new NoSuchElementException("Contribution not found."));
	}
}
	

