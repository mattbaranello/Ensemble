package ensemble.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ensemble.controller.model.ProjectData;
import ensemble.controller.model.ProjectData.ProjectSongwriter;
import ensemble.controller.model.ProjectData.ProjectSongwriter.ContributionData;
import ensemble.service.EnsembleService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ensemble")
@Slf4j
public class EnsembleController {
	@Autowired
	private EnsembleService ensembleService;
	
	
	@PostMapping("/project")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProjectData createEnsembleProject(@RequestBody ProjectData projectData) {
		log.info("Creating ensemble project {}", projectData);
		return ensembleService.saveProject(projectData);
	}
	
	@PutMapping("/project/{projectId}")
	public ProjectData updateEnsembleProject(
			@PathVariable Long projectId, @RequestBody ProjectData projectData) {
		log.info("Update project with ID={}", projectId);
		projectData.setProjectId(projectId);
		return ensembleService.saveProject(projectData);
	}
	
	@GetMapping("/project/{projectId}")
	public ProjectData retrieveEnsembleProject(@PathVariable Long projectId) {
		log.info("Retrieving Ensemble project with ID={}", projectId);
		return ensembleService.retrieveEnsembleProjectById(projectId);
	}
	
	@DeleteMapping("/project/{projectId}")
	public Map<String, String> deleteProject(@PathVariable Long projectId) {
		log.info("Deleting Ensemble project with ID=" + projectId + ".");
		ensembleService.deleteProject(projectId);
		
		return Map.of("message", "Esemble project with ID=" + projectId
				+ " was deleted successfully.");
	}
	
	@PostMapping("/project/{projectId}/songwriter")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProjectSongwriter addSongWriterToProject(
			@PathVariable Long projectId, @RequestBody ProjectSongwriter projectSongwriter) {
		log.info("Add songwriter to project with ID={}", projectSongwriter);
		return ensembleService.saveSongwriter(projectId, projectSongwriter);
	}
	
	@PutMapping("project/{projectId}/songwriter")
	public ProjectSongwriter updateSongwriter(
			@PathVariable Long projectId, @RequestBody ProjectSongwriter projectSongwriter) {
		log.info("Update songwriter with ID={}", projectId);
		projectSongwriter.setSongwriterId(projectId);
		return ensembleService.saveSongwriter(projectId, projectSongwriter);
	}
	
	@PostMapping("/songwriter/{songwriterId}/contribution")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ContributionData addSongwriterContribution(
			@PathVariable Long songwriterId, @RequestBody ContributionData contributionData) {
		log.info("Add songwriter contribution with ID={}", contributionData);
		return ensembleService.saveSongwriterContribution(songwriterId, contributionData);
	}
	
}
