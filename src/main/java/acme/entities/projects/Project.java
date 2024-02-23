
package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
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
	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}")
	@NotBlank
	private String				code;

	@Length(max = 75)
	@NotBlank
	private String				title;

	@Length(max = 100)
	@NotBlank
	private String				abstract_;

	private boolean				hasFatalErrors;

	@NotNull
	@Valid
	private Money				cost;

	@URL
	private String				link;


	public Project(final String code, final String title, final String abstract_, final Boolean hasFatalErrors, final Money cost, final String link) {
		if (hasFatalErrors)
			throw new IllegalArgumentException("A project cannot contain fatal errors.");
		if (cost.getAmount() < 0)
			throw new IllegalArgumentException("The amount of the cost must be positive or nought.");

		this.code = code;
		this.title = title;
		this.abstract_ = abstract_;
		this.hasFatalErrors = hasFatalErrors;
		this.cost = cost;
		this.link = link;
	}

}
