
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
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

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
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "displayStartMoment", "displayEndMoment", "picture", "slogan", "target");
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayStartMoment"))
			super.state(MomentHelper.isAfter(object.getDisplayStartMoment(), object.getMoment()), "displayStartMoment", "administrator.banner.form.error.startDate");

		if (!super.getBuffer().getErrors().hasErrors("displayEndMoment"))
			super.state(MomentHelper.isAfter(object.getDisplayEndMoment(), object.getMoment()), "displayEndMoment", "administrator.banner.form.error.endDate");

		if (!super.getBuffer().getErrors().hasErrors("displayEndMoment"))
			super.state(MomentHelper.isLongEnough(object.getDisplayStartMoment(), object.getDisplayEndMoment(), 1, ChronoUnit.WEEKS), "displayEndMoment", "administrator.banner.form.error.period");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "displayStartMoment", "displayEndMoment", "picture", "slogan", "target");
		dataset.put("moment", MomentHelper.getCurrentMoment());

		super.getResponse().addData(dataset);
	}

}
