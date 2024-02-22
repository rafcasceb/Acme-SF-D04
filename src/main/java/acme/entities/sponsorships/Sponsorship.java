
package acme.entities.sponsorships;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
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
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@NotBlank
	private String				code;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@NotNull
	private Date				moment;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@NotNull
	private Date				startDate;

	@Temporal(TemporalType.DATE)
	@PastOrPresent
	@NotNull
	private Date				endDate;

	@Min(0)
	@NotNull
	private double				amount;

	@NotNull
	private SponsorshipType		type;

	@Email
	private String				email;

	@URL
	private String				link;

	// Derived Attributes -------------------------------------------------------------------------------


	@Transient
	public Duration getDuration() {
		return Duration.between(this.startDate.toInstant(), this.endDate.toInstant());
	}
	// Validation  ------------------------------------------------------------

	@AssertTrue(message = "The duration must be greater than one month")
	public boolean isTimePeriodGreaterThanOneWeek() {
		Duration oneMonth = Duration.ofDays(30);
		return this.getDuration().compareTo(oneMonth) >= 0;
	}

	@AssertTrue(message = "The start date must be after moment")
	public boolean isStartDateAfterMoment() {
		return this.startDate.after(this.moment);
	}

}
