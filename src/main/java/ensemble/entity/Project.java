package ensemble.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;
	
	private Date creationDate;
	private String name;
	private int bpm;
	private String genre;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Songwriter> songwriters = new HashSet<>();
}
