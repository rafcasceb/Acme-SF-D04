
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
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
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setRegistrationTime(moment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "link", "dueDate", "quantity", "tax", "sponsorship");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice invoiceSameCode;
			invoiceSameCode = this.repository.findInvoiceByCode(object.getCode());
			int id = invoiceSameCode.getId();
			super.state(id == object.getId() || invoiceSameCode == null, "code", "sponsor.invoice.form.error.duplicate");
		}

		if (!super.getBuffer().getErrors().hasErrors("dueDate"))
			super.state(MomentHelper.isAfter(object.getDueDate(), MomentHelper.getCurrentMoment()), "dueDate", "sponsor.invoice.form.error.dueDate");

		if (!super.getBuffer().getErrors().hasErrors("dueDate"))
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 1, ChronoUnit.MONTHS), "dueDate", "sponsor.invoice.form.error.period");

		if (!super.getBuffer().getErrors().hasErrors("sponsorship"))
			super.state(object.getSponsorship().isPublished() == false, "sponsorship", "sponsor.invoice.form.error.sponsorship");

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
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

		dataset = super.unbind(object, "code", "link", "registrationTime", "dueDate", "quantity", "tax");
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
