
package acme.forms;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Min(value = 0)
	@Max(value = 1000000)
	private Integer				totalNumberInvoicesTaxEqualOrLessThan21;

	@Min(value = 0)
	@Max(value = 1000000)
	private Integer				totalNumberSponsorshipsWithLink;

	@DecimalMin(value = "0.0")
	@DecimalMax(value = "1000000.00")
	@Digits(integer = 7, fraction = 2)
	private Double				averageSponsorships;

	@DecimalMin(value = "0.0")
	@DecimalMax(value = "1000000.00")
	@Digits(integer = 7, fraction = 2)
	private Double				deviationSponsorships;

	@Min(value = 0)
	@Max(value = 1000000)
	private Integer				minimumQuantitySponsorships;

	@Min(value = 0)
	@Max(value = 1000000)
	private Integer				maximumQuantitySponsorships;

	@DecimalMin(value = "0.0")
	@DecimalMax(value = "1000000.00")
	@Digits(integer = 7, fraction = 2)
	private Double				averageInvoices;

	@DecimalMin(value = "0.0")
	@DecimalMax(value = "1000000.00")
	@Digits(integer = 7, fraction = 2)
	private Double				deviationInvoices;

	@Min(value = 0)
	@Max(value = 1000000)
	private Integer				minimumInvoices;

	@Min(value = 0)
	@Max(value = 1000000)
	private Integer				maximumInvoices;

}
