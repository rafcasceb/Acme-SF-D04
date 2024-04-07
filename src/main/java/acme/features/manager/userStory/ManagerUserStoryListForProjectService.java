
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListForProjectService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;
		Manager manager;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findOneProjectById(projectId);
		manager = project == null ? null : project.getManager();
		status = project != null && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		objects = this.repository.findManyUserStoriesByProjectId(projectId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "published", "title", "priority");

		super.getResponse().addData(dataset);
	}

}
