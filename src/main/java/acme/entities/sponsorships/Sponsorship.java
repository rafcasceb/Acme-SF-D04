
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "{validation.sponsorship.code}")
	@NotBlank
	private String				code;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@NotNull
	private Date				moment;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date				startDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date				endDate;

	@NotNull
	private Money				amount;


	@NotNull
	private SponsorshipType		type;

	@Email
	@Length(max = 255)
	private String				email;

	@URL
	@Length(max = 255)
	private String				link;

	// Relationships  ------------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne()
	private Project				project;


}
