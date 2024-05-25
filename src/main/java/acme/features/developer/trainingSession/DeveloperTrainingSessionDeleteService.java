
package acme.features.developer.trainingSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionDeleteService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int sessionId;
		TrainingModule module;

		sessionId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTrainingModuleByTrainingSessionId(sessionId);
		status = module != null && !module.isPublished() && super.getRequest().getPrincipal().hasRole(module.getDeveloper());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		TrainingSession session;
		int id;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTrainingSessionById(id);

		super.getBuffer().addData(session);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startDate", "endDate", "location", "instructor", "email", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	/*
	 * * Here you would normally add the following code. However, as the delete has no validations,
	 * it doesn't make sense to keep this code here.
	 * 
	 * @Override
	 * public void unbind(final TrainingSession object) {
	 * /*
	 * 
	 * 
	 * assert object != null;
	 * 
	 * Dataset dataset;
	 * 
	 * dataset = super.unbind(object, "code", "startDate", "endDate", "location", "instructor", "email", "link", "published");
	 * dataset.put("masterId", object.getTrainingModule().getId());
	 * 
	 * super.getResponse().addData(dataset);
	 * 
	 * 
	 * }
	 */

}
