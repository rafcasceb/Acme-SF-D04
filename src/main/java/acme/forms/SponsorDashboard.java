
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	int							totalNumberInvoicesTaxEqualOrLessThan21;
	int							totalNumberSponsorshipsWithLink;

	Double						averageAmountSponsorships;
	Double						deviationAmountSponsorships;
	Integer						minimumAmountSponsorships;
	Integer						maximumAmountSponsorships;

	Double						averageQuantityInvoices;
	Double						deviationQuantityInvoices;
	Integer						minimumQuantityInvoices;
	Integer						maximumQuantityInvoices;

}
