
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.entities.projects.UserStoryPriority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long				serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	private Map<UserStoryPriority, Integer>	totalNumberUserStoriesByPriority;

	private Double							averageEstimatedCostUserStories;
	private Double							deviationEstimatedCostUserStories;
	private Integer							minimumEstimatedCostUserStories;
	private Integer							maximumEstimatedCostUserStories;

	private Double							averageCostProjects;
	private Double							deviationCostProjects;
	private Integer							minimumCostProjects;
	private Integer							maximumCostProjects;
}
