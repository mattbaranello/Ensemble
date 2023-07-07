package ensemble.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Songwriter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long songwriterId;
	
	private String songwriterUsername;
	private String songwriterEmail;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id")
	private Project project;
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "songwriter_contribution",
			joinColumns = @JoinColumn(name = "songwriter_id"),
			inverseJoinColumns = @JoinColumn(name = "contribution_id"))
	
	private Set<Contribution> contributions = new HashSet<>();
}
