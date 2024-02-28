
package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "{validation.project.code}")
	@NotBlank
	private String				code;

	@Length(max = 75)
	@NotBlank
	private String				title;

	@Length(max = 100)
	@NotBlank
	private String				abstract_;

	private boolean				fatalErrorPresent;

	@URL
	private String				link;

	private boolean				published;		// Indicates if aggregation is not in draft mode.

}
