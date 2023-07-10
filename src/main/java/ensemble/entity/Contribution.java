package ensemble.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Contribution {
	//Creates entity table with fields below.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contributionId;
	
	private String timestamp;
	
	/*
	 * Creates a many to one relationship between "Contribution" and "Project".
	 * Joins column "project_id" as a foreign key.
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	
	/*
	 * Creates a many to many relationship between "Contribution" and "Songwriter". 
	 * Creates a set of songwriters.
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "contributions", cascade = CascadeType.PERSIST)
	private Set<Songwriter> songwriters = new HashSet<>();

}
