
package acme.features.developer.trainingModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.trainingmodule.DifficultyLevel;
import acme.entities.trainingmodule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

	// Internal state -----------------------------------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	//AbstractService interface 


	@Override
	public void authorise() {
		boolean status;
		int id;
		TrainingModule trainingModule;

		id = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(id);
		status = MomentHelper.isAfter(trainingModule.getUpdateMoment(), trainingModule.getCreationMoment());

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
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;

		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "code", "details", "difficultyLevel", "creationMoment", "updateMoment", "link", "estimatedTotalTime", "published", "project.title");
		dataset.put("difficultyLevel", choices.getSelected().getKey());
		dataset.put("difficultyLevels", choices);

		super.getResponse().addData(dataset);
	}

}
