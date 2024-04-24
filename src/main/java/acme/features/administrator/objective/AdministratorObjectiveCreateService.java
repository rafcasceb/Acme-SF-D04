
package acme.features.administrator.objective;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objectives.Objective;
import acme.entities.objectives.ObjectiveStatus;

@Service
public class AdministratorObjectiveCreateService extends AbstractService<Administrator, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Objective object;
		Date instantiationMoment;
		instantiationMoment = MomentHelper.getCurrentMoment();
		object = new Objective();
		object.setInstantiationMoment(instantiationMoment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;

		super.bind(object, "title", "description", "priority", "critical", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final Objective object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("confirmation")) {
			boolean confirmation;
			confirmation = super.getRequest().getData("confirmation", boolean.class);
			super.state(confirmation, "confirmation", "administrator.objective.form.error.confirmation");
		}
		Date instantiationMoment = MomentHelper.getCurrentMoment();
		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isAfter(object.getStartDate(), instantiationMoment), "startDate", "validation.objective.moment.startDate");

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			Date minimumEnd;

			minimumEnd = MomentHelper.deltaFromMoment(object.getStartDate(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndDate(), minimumEnd), "endDate", "validation.objective.moment.minimum-one-hour");
		}
	}

	@Override
	public void perform(final Objective object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;
		choices = SelectChoices.from(ObjectiveStatus.class, object.getPriority());

		dataset = super.unbind(object, "title", "description", "priority", "critical", "startDate", "endDate", "link");
		dataset.put("confirmation", false);
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}

}
