
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;

public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "R-[0-9]{3}")
	@NotBlank
	private String				reference;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@NotNull
	private Date				identificationDate;

	@Min(0)
	@NotNull
	private double				impact;

	@Min(0)
	@Max(100)
	@NotNull
	private double				probability;

	@Length(max = 100)
	@NotBlank
	private String				description;

	@URL
	private String				link;


	@Transient
	public Double getValue() {
		return this.impact * this.probability;
	}

}
