
package acme.features.manager.userStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.SpamDetector;
import acme.entities.configuration.Configuration;
import acme.entities.projects.UserStory;
import acme.entities.projects.UserStoryPriority;
import acme.roles.Manager;

@Service
public class ManagerUserStoryUpdateService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		UserStory userStory;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		userStory = this.repository.findOneUserStoryById(masterId);
		manager = userStory == null ? null : userStory.getManager();
		status = userStory != null && !userStory.isPublished() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCostInHours", "acceptanceCriteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;

		Configuration config = this.repository.findConfiguration();
		String spamTerms = config.getSpamTerms();
		Double spamThreshold = config.getSpamThreshold();
		SpamDetector spamHelper = new SpamDetector(spamTerms, spamThreshold);

		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(!spamHelper.isSpam(object.getTitle()), "title", "manager.user-story.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("description"))
			super.state(!spamHelper.isSpam(object.getDescription()), "description", "manager.user-story.form.error.spam");

		if (!super.getBuffer().getErrors().hasErrors("acceptanceCriteria"))
			super.state(!spamHelper.isSpam(object.getAcceptanceCriteria()), "acceptanceCriteria", "manager.user-story.form.error.spam");
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(UserStoryPriority.class, object.getPriority());
		dataset = super.unbind(object, "published", "title", "description", "estimatedCostInHours", "acceptanceCriteria", "priority", "link");
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}

}
