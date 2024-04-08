
package acme.features.manager.projectUserStory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Controller
public class ManagerProjectUserStoryController extends AbstractController<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryCreateService	createService;
	@Autowired
	private ManagerProjectUserStoryDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}

}
