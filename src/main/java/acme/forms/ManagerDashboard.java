
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	Integer						totalNumberMustUserStories;
	Integer						totalNumberShouldUserStories;
	Integer						totalNumberCouldUserStories;
	Integer						totalNumberWontUserStories;

	Double						averageEstimatedCostUserStories;
	Double						deviationEstimatedCostUserStories;
	Integer						minimumEstimatedCostUserStories;
	Integer						maximumEstimatedCostUserStories;

	Double						averageCostProjects;
	Double						deviationCostProjects;
	Integer						minimumCostProjects;
	Integer						maximumCostProjects;
}
