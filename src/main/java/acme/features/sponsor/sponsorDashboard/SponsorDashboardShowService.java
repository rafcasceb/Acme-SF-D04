
package acme.features.sponsor.sponsorDashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		int totalNumberInvoicesTaxEqualOrLessThan21 = this.repository.countInvoicesWithTaxLessThanOrEqual21(sponsorId);
		int totalNumberSponsorshipsWithLink = this.repository.countSponsorshipsWithLink(sponsorId);
		Double averageAmountSponsorships = this.repository.averageAmountSponsorships(sponsorId, "EUR");
		Double averageAmountSponsorshipsUSD = this.repository.averageAmountSponsorships(sponsorId, "USD");
		Double stdevAmountSponsorships = this.repository.stdevAmountSponsorships(sponsorId);
		Double minimumAmountSponsorships = this.repository.minimumAmountSponsorships(sponsorId);
		Double maximumAmountSponsorships = this.repository.maximumAmountSponsorships(sponsorId);
		Double averageQuantityInvoices = this.repository.averageQuantityInvoices(sponsorId);
		Double stdevQuantityInvoices = this.repository.stdevQuantityInvoices(sponsorId);
		Double minimumQuantityInvoices = this.repository.minimumQuantityInvoices(sponsorId);
		Double maximumQuantityInvoices = this.repository.maximumQuantityInvoices(sponsorId);

		List<Double> averages = new ArrayList<>();
		averages.add(averageAmountSponsorships);
		averages.add(averageAmountSponsorshipsUSD);

		SponsorDashboard dashboard = new SponsorDashboard();
		dashboard.setTotalNumberInvoicesTaxEqualOrLessThan21(totalNumberInvoicesTaxEqualOrLessThan21);
		dashboard.setTotalNumberSponsorshipsWithLink(totalNumberSponsorshipsWithLink);
		dashboard.setAverageAmountSponsorships(averages);
		dashboard.setStdevAmountSponsorships(stdevAmountSponsorships);
		dashboard.setMinimumAmountSponsorships(minimumAmountSponsorships);
		dashboard.setMaximumAmountSponsorships(maximumAmountSponsorships);
		dashboard.setAverageQuantityInvoices(averageQuantityInvoices);
		dashboard.setStdevQuantityInvoices(stdevQuantityInvoices);
		dashboard.setMinimumQuantityInvoices(minimumQuantityInvoices);
		dashboard.setMaximumQuantityInvoices(maximumQuantityInvoices);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		assert object != null;

		Dataset dataset = super.unbind(object, //
			"totalNumberInvoicesTaxEqualOrLessThan21", "totalNumberSponsorshipsWithLink", //
			"averageAmountSponsorships", "stdevAmountSponsorships", //
			"minimumAmountSponsorships", "maximumAmountSponsorships", //
			"averageQuantityInvoices", "stdevQuantityInvoices", //
			"minimumQuantityInvoices", "maximumQuantityInvoices");

		dataset.put("averageAmountSponsorshipsUSD", object.getAverageAmountSponsorships().get(1));
		dataset.put("averageAmountSponsorshipsEUR", object.getAverageAmountSponsorships().get(0));

		super.getResponse().addData(dataset);
	}
}
