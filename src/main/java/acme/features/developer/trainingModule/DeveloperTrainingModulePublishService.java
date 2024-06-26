
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.SpamDetector;
import acme.entities.configuration.Configuration;
import acme.entities.projects.Project;
import acme.entities.trainingModule.DifficultyLevel;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
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

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "details", "difficultyLevel", "link", "estimatedTotalTime", "project");
		object.setProject(project);

	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findOneTrainingModuleByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-module.form.error.duplicated");
		}

		if (object.getUpdateMoment() != null && !super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(MomentHelper.isAfter(object.getUpdateMoment(), object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.update-date-not-valid");

		{
			Collection<TrainingSession> sessions;
			int totalSessions;
			boolean publishedSessions = true;

			sessions = this.repository.findManyTrainingSessionByModuleId(object.getId());
			totalSessions = sessions.size();
			super.state(totalSessions >= 1, "*", "developer.training-module.form.error.no-training-sessions");
			for (TrainingSession ts : sessions)
				if (!ts.isPublished()) {
					publishedSessions = false;
					break;
				}
			super.state(publishedSessions, "*", "developer.training-module.form.error.no-training-sessions-published");

		}

		if (!super.getBuffer().getErrors().hasErrors("details")) {
			Configuration config = this.repository.findConfiguration();
			String spamTerms = config.getSpamTerms();
			Double spamThreshold = config.getSpamThreshold();
			SpamDetector spamHelper = new SpamDetector(spamTerms, spamThreshold);
			super.state(!spamHelper.isSpam(object.getDetails()), "details", "validation.training-module.form.error.spam");
		}
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setUpdateMoment(moment);

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		projects = this.repository.findManyProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "details", "difficultyLevel", "link", "estimatedTotalTime", "published", "project", "creationMoment");
		dataset.put("difficultyLevels", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);
	}

}
