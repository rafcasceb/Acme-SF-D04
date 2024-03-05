
package acme.entities.trainingmodule;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingModule extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "{validation.project.code}")
	@NotBlank
	private String				code;

	@Length(max = 100)
	@NotBlank
	private String				details;

	@NotNull
	private DifficultyLevel		difficultyLevel;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				creationMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				updateMoment;

	@URL
	private String				link;

	@Positive
	@Max(200)
	private int					estimatedTotalTime;

}
