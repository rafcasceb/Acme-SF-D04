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

package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banners.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		Date moment;
		Date startMoment;
		Date endMoment;
		long aDayInMs = (long) 24 * 60 * 60 * 1000;

		moment = MomentHelper.getCurrentMoment();
		startMoment = new Date(moment.getTime() + aDayInMs);
		endMoment = new Date(startMoment.getTime() + aDayInMs * 7);

		object = new Banner();
		object.setMoment(moment);
		object.setDisplayStartMoment(startMoment);
		object.setDisplayEndMoment(endMoment);
		object.setPicture("");
		object.setSlogan("");
		object.setTarget("");

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "displayStartMoment", "displayEndMoment", "picture", "slogan", "target");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		boolean confirmation;

		if (!super.getBuffer().getErrors().hasErrors("displayStartMoment"))
			super.state(MomentHelper.isAfter(object.getDisplayStartMoment(), object.getMoment()), "displayStartMoment", "administrator.banner.form.error.startDate");

		if (!super.getBuffer().getErrors().hasErrors("displayEndMoment"))
			super.state(MomentHelper.isAfter(object.getDisplayEndMoment(), object.getMoment()), "displayEndMoment", "administrator.banner.form.error.endDate");

		if (!super.getBuffer().getErrors().hasErrors("displayEndMoment"))
			super.state(MomentHelper.isLongEnough(object.getDisplayStartMoment(), object.getDisplayEndMoment(), 1, ChronoUnit.WEEKS), "displayEndMoment", "administrator.banner.form.error.period");
		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "moment", "displayStartMoment", "displayEndMoment", "picture", "slogan", "target");
		dataset.put("confirmation", false);
		dataset.put("readonly", false);

		super.getResponse().addData(dataset);
	}

}
