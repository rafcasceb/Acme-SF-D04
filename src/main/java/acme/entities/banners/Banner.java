
package acme.entities.banners;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				displayStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				displayEndDate;

	@NotBlank
	@URL
	@Length(max = 255)
	private String				picture;

	@NotBlank
	@Length(max = 75)
	private String				slogan;

	@NotBlank
	@URL
	@Length(max = 255)
	private String				target;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Duration getDisplayPeriod() {
		return Duration.between(this.displayStartDate.toInstant(), this.displayEndDate.toInstant());
	}

	// toString method
	@Override
	public String toString() {
		return "Banner{" + "moment=" + this.moment + ", displayStartDate=" + this.displayStartDate + ", displayEndDate=" + this.displayEndDate + ", picture='" + this.picture + '\'' + ", slogan='" + this.slogan + '\'' + ", target='" + this.target + '\''
			+ '}';
	}

	// Relationships ----------------------------------------------------------

	// Validation  ------------------------------------------------------------

	@AssertTrue(message = "The display period must be greater than one week")
	public boolean isTimePeriodGreaterThanOneWeek() {
		Duration oneWeek = Duration.ofDays(7);
		return this.getDisplayPeriod().compareTo(oneWeek) >= 0;
	}

	@AssertTrue(message = "The display start date must be after the instantiation moment")
	public boolean isDisplayStartDateAfterMoment() {
		return this.displayStartDate.after(this.moment);
	}

	// Constructor
	public Banner(final Date moment, final Date displayStartDate, final Date displayEndDate, final String picture, final String slogan, final String target) {
		this.moment = moment;
		this.displayStartDate = displayStartDate;
		this.displayEndDate = displayEndDate;
		this.picture = picture;
		this.slogan = slogan;
		this.target = target;

		// Check validation
		if (!this.isTimePeriodGreaterThanOneWeek())
			throw new IllegalArgumentException("Display period lower than 1 week.");
		if (!this.isDisplayStartDateAfterMoment())
			throw new IllegalArgumentException("Start display date before instantiation date.");
	}

}
