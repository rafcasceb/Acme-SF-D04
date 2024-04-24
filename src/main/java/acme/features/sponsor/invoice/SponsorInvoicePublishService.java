
package acme.features.sponsor.invoice;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService<Auditor, CodeAudit> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Sponsor sponsor;
		Invoice invoice;

		id = super.getRequest().getData("id", int.class);
		invoice = this.repository.findOneInvoiceById(id);

		sponsor = invoice == null ? null : invoice.getSponsorship().getSponsor();
		status = invoice != null && !invoice.isPublished() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		Invoice object;
		int id;
		Date instantiationMoment;
		instantiationMoment = MomentHelper.getCurrentMoment();

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);
		object.setRegistrationTime(instantiationMoment);
		object.setPublished(false);

		super.getBuffer().addData(object);

	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, "published");

	}

	@Override
	public void validate(final Invoice object) {

		assert object != null;
		double total = 0.0;

		if (!super.getBuffer().getErrors().hasErrors("sponsorship")) {
			Collection<Invoice> invoices = this.repository.findAllInvoicesBySponsorshipId(object.getSponsorship().getId());
			for (Invoice invoice : invoices)
				if (invoice.isPublished())
					total += invoice.getValue().getAmount();

			super.state(total + object.getValue().getAmount() <= object.getSponsorship().getAmount().getAmount(), "sponsorship", "invoice.sponsorship.form.error.amount");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getSponsorship() != null && object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.currency");

	}

	@Override
	public void perform(final Invoice object) {

		assert object != null;
		object.setPublished(true);
		this.repository.save(object);

	}

	@Override
	public void unbind(final Invoice object) {

		assert object != null;

		Dataset dataset;
		SelectChoices sponsorships;
		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		Collection<Sponsorship> sponsorSponsorships = this.repository.findSponsorshipBySponsorId(sponsorId);
		sponsorships = SelectChoices.from(sponsorSponsorships, "code", object.getSponsorship());

		dataset = super.unbind(object, "code", "link", "registrationTime", "dueDate", "quantity", "tax", "published");
		Sponsorship selectedSponsorship = this.repository.findOneSponsorshipById(Integer.valueOf(sponsorships.getSelected().getKey()));
		dataset.put("sponsorship", sponsorships.getSelected().getKey());

		sponsorSponsorships = this.repository.findSponsorUnpublishedSponsorship(sponsorId);
		if (!sponsorSponsorships.contains(selectedSponsorship) && selectedSponsorship != null)
			sponsorSponsorships.add(selectedSponsorship);

		sponsorships = SelectChoices.from(sponsorSponsorships, "code", object.getSponsorship());
		dataset.put("sponsorships", sponsorships);

		super.getResponse().addData(dataset);

	}

}
