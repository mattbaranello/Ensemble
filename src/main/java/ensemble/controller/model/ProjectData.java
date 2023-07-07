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
	
	public ProjectData(Project project) {
		this.projectId = project.getProjectId();
		this.creationDate = project.getCreationDate();
		this.name = project.getName();
		this.bpm = project.getBpm();
		this.genre = project.getGenre();
		
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
			
			public ProjectSongwriter(Songwriter songwriter) {
				this.songwriterId = songwriter.getSongwriterId();
				this.songwriterUsername = songwriter.getSongwriterUsername();
				this.songwriterEmail = songwriter.getSongwriterEmail();
				
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
			
			public ContributionData(Contribution contribution) {
				this.contributionId = contribution.getContributionId();
				this.timestamp = contribution.getTimestamp();
				
			for(Songwriter songwriter : contribution.getSongwriters()) {
				this.projectSongwriters.add(new ProjectSongwriter(songwriter));
				}
			}	
		}
	}
}


