
package acme.entities.audits;

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
public class Record extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}", message = "{validation.record.code}")
	private String				code;

	@URL
	@Length(max = 255)
	private String				link;

	// Missing: relationship with audits, attribute "period" and a mark (“A+”, “A”, “B”, “C”, “F”, or “F-”).
}
