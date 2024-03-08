
package acme.entities.audits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	private boolean				draftMode;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "{validation.audit.code}")
	private String				code;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@NotNull
	private Date				execution;

	@NotNull
	private AuditType			type;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	@URL
	@Length(max = 255)
	private String				link;

	// Derived Attributes --------------------------------------------------------

	// TODO: Attribute "mark" which is the mode mark obtained from its records.
	@Transient
	private Mark				mark;

	// Relationships  ------------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

}
