package ensemble.controller.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ensemble.entity.Contribution;
import ensemble.entity.Project;
import ensemble.entity.Songwriter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectData {

	private Long projectId;
	private Date creationDate;
	private String name;
	private int bpm;
	private String genre;
	
	private Set<ProjectSongwriter> projectSongwriters = new HashSet<>();
	
	// Constructor to create a ProjectData object from a Project
	public ProjectData(Project project) {
		this.projectId = project.getProjectId();
		this.creationDate = project.getCreationDate();
		this.name = project.getName();
		this.bpm = project.getBpm();
		this.genre = project.getGenre();
		
		/*
		 * Iterate over each Songwriter in the Project's songwriters list
		 * Create a new ProjectSongwriter object using the current Songwriter
		 * 	and add it to the projectSongwriters set
		 */
		for(Songwriter songwriter : project.getSongwriters()) {
			this.projectSongwriters.add(new ProjectSongwriter(songwriter));
			}
		}
		
		@Data
		@NoArgsConstructor
		public static class ProjectSongwriter {

			private Long songwriterId;
			private String songwriterUsername;
			private String songwriterEmail;
			private Set<ContributionData> contributionData = new HashSet<>();
			
			// Constructor to create a ProjectSongwriter object from a Songwriter
			public ProjectSongwriter(Songwriter songwriter) {
				this.songwriterId = songwriter.getSongwriterId();
				this.songwriterUsername = songwriter.getSongwriterUsername();
				this.songwriterEmail = songwriter.getSongwriterEmail();
			
			/*
			 *  Iterate over each Contribution in the Songwriter's contributions list
			 *  Create a new ContributionData object using the current Contribution
			 *	and add it to the contributionData set
			 */ 
			for(Contribution contribution : songwriter.getContributions()) {
				this.contributionData.add(new ContributionData(contribution));
			}
		}
			
		@Data
		@NoArgsConstructor
		public static class ContributionData {
			
			private Long contributionId;
			private String timestamp;
			private Set<ProjectSongwriter> projectSongwriters = new HashSet<>();
			
			// Constructor to create a ContributionData object from a Contribution
			public ContributionData(Contribution contribution) {
				contributionId = contribution.getContributionId();
				timestamp = contribution.getTimestamp();
			
			/* Iterate over each Songwriter in the Contribution's songwriters list
			 * Create a new ProjectSongwriter object using the current Songwriter
			 * and add it to the projectSongwriters set
			 */ 
			for(Songwriter songwriter : contribution.getSongwriters()) {
				projectSongwriters.add(new ProjectSongwriter(songwriter));
				}
			}	
		}
	}
}


