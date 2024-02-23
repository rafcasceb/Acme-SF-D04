
package acme.forms;

import acme.client.data.AbstractForm;

public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	Integer						totalNumberInvoicesTaxEqualOrLessThan21;
	Integer						totalNumberSponsorshipsWithLink;

	Double						averageSponsorships;
	Double						deviationSponsorships;
	Integer						minimumQuantitySponsorships;
	Integer						maximumQuantitySponsorships;

	Double						averageInvoices;
	Double						deviationInvoices;
	Integer						minimumInvoices;
	Integer						maximumInvoices;

}
