
package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
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
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "R-[0-9]{3}", message = "Use a pattern starting with 'R-' followed by three digits, each from 0 to 9.")
	@NotBlank
	private String				reference;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@NotNull
	private Date				identificationDate;

	@Positive
	private double				impact;

	@Digits(integer = 1, fraction = 3)
	private double				probability;

	@Length(max = 100)
	@NotBlank
	private String				description;

	@URL
	@Length(max = 255)
	private String				link;


	@Transient
	public Double getValue() {
		return this.impact * this.probability;
	}

}
