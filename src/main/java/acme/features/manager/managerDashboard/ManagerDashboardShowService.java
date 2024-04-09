
package acme.features.manager.managerDashboard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.UserStoryPriority;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int managerId;
		ManagerDashboard dashboard;

		Map<UserStoryPriority, Integer> totalNumberUserStoriesByPriority;
		Double averageEstimatedCostUserStories;
		Double deviationEstimatedCostUserStories;
		Integer minimumEstimatedCostUserStories;
		Integer maximumEstimatedCostUserStories;
		Double averageCostProjects;
		Double deviationCostProjects;
		Integer minimumCostProjects;
		Integer maximumCostProjects;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		totalNumberUserStoriesByPriority = this.repository.totalNumberUserStoriesByPriority(managerId);
		averageEstimatedCostUserStories = this.repository.averageEstimatedCostUserStories(managerId);
		deviationEstimatedCostUserStories = this.repository.deviationEstimatedCostUserStories(managerId);
		minimumEstimatedCostUserStories = this.repository.minimumEstimatedCostUserStories(managerId);
		maximumEstimatedCostUserStories = this.repository.maximumEstimatedCostUserStories(managerId);
		averageCostProjects = this.repository.averageCostProjects(managerId);
		deviationCostProjects = this.repository.deviationCostProjects(managerId);
		minimumCostProjects = this.repository.minimumCostProjects(managerId);
		maximumCostProjects = this.repository.maximumCostProjects(managerId);

		dashboard = new ManagerDashboard();
		dashboard.setTotalNumberUserStoriesByPriority(totalNumberUserStoriesByPriority);
		dashboard.setAverageEstimatedCostUserStories(averageEstimatedCostUserStories);
		dashboard.setDeviationEstimatedCostUserStories(deviationEstimatedCostUserStories);
		dashboard.setMinimumEstimatedCostUserStories(minimumEstimatedCostUserStories);
		dashboard.setMaximumEstimatedCostUserStories(maximumEstimatedCostUserStories);
		dashboard.setAverageCostProjects(averageCostProjects);
		dashboard.setDeviationCostProjects(deviationCostProjects);
		dashboard.setMinimumCostProjects(minimumCostProjects);
		dashboard.setMaximumCostProjects(maximumCostProjects);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		assert object != null;

		Dataset dataset;
		Integer totalNumberUserStoriesPriorityMust;
		Integer totalNumberUserStoriesPriorityShould;
		Integer totalNumberUserStoriesPriorityCould;
		Integer totalNumberUserStoriesPriorityWont;

		totalNumberUserStoriesPriorityMust = object.getTotalNumberUserStoriesByPriority().get(UserStoryPriority.MUST);
		totalNumberUserStoriesPriorityShould = object.getTotalNumberUserStoriesByPriority().get(UserStoryPriority.SHOULD);
		totalNumberUserStoriesPriorityCould = object.getTotalNumberUserStoriesByPriority().get(UserStoryPriority.COULD);
		totalNumberUserStoriesPriorityWont = object.getTotalNumberUserStoriesByPriority().get(UserStoryPriority.WONT);

		dataset = super.unbind(object, //
			"averageEstimatedCostUserStories", "deviationEstimatedCostUserStories", //
			"minimumEstimatedCostUserStories", "maximumEstimatedCostUserStories", //
			"averageCostProjects", "deviationCostProjects", "minimumCostProjects", "maximumCostProjects");

		dataset.put("totalNumberUserStoriesPriorityMust", totalNumberUserStoriesPriorityMust);
		dataset.put("totalNumberUserStoriesPriorityShould", totalNumberUserStoriesPriorityShould);
		dataset.put("totalNumberUserStoriesPriorityCould", totalNumberUserStoriesPriorityCould);
		dataset.put("totalNumberUserStoriesPriorityWont", totalNumberUserStoriesPriorityWont);

		super.getResponse().addData(dataset);
	}

}
