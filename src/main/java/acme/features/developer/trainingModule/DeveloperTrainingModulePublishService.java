
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.UserStory;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingmodule.TrainingSession;
import acme.roles.Developer;

public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && !trainingModule.isPublished() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "details", "difficultyLevel", "creationMoment", "updateMoment", "link", "estimatedTotalTime", "published", "project.title");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		Collection<TrainingSession> trainingSessions;

		trainingSessions = this.repository.findManyTrainingSessionByModuleId(object.getId());
		super.state(!trainingSessions.isEmpty(), "*", "developer.trainingModule.form.error.no-training-sessions");
	}


	}


	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "details", "difficultyLevel", "creationMoment", "updateMoment", "link", "estimatedTotalTime", "published", "project.title");

		super.getResponse().addData(dataset);
	}

}
