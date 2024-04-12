/*
 * AdministratorAnnouncementCreateService.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.sponsor.sponsorship;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object;
		Date moment;
		moment = MomentHelper.getCurrentMoment();

		object = new Sponsorship();
		object.setMoment(moment);
		object.setCode("");
		object.setStartDate(moment);
		object.setEndDate(moment);
		object.setLink("");
		object.setEmail("");
		object.setPublished(false);
		object.setType(SponsorshipType.FINANCIAL);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		super.bind(object, "code", "moment", "startDate", "endDate", "amount", "email", "link", "published");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "email", "link", "published");
		dataset.put("readonly", false);

		super.getResponse().addData(dataset);
	}

}
