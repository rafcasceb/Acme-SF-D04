
package acme.features.manager.userStory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryController extends AbstractController<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryShowService				showService;
	@Autowired
	private ManagerUserStoryCreateService			createService;
	@Autowired
	private ManagerUserStoryUpdateService			updateService;
	@Autowired
	private ManagerUserStoryDeleteService			deleteService;

	@Autowired
	private ManagerUserStoryListMineService			listMineService;
	@Autowired
	private ManagerUserStoryListForProjectService	listForProjectService;
	@Autowired
	private ManagerUserStoryPublishService			publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("list-for-project", "list", this.listForProjectService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
