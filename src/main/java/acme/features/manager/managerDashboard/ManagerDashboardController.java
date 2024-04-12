
package acme.features.manager.managerDashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Controller
public class ManagerDashboardController extends AbstractController<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
