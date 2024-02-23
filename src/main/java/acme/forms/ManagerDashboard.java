
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

	Double						totalNumberMustUserStories;
	Double						totalNumberShouldUserStories;
	Double						totalNumberCouldUserStories;
	Double						totalNumberWontUserStories;

	Double						averageEstimatedCostUserStories;
	Double						deviationEstimatedCostUserStories;
	Double						minimumEstimatedCostUserStories;
	Double						maximumEstimatedCostUserStories;

	Double						averageCostProjects;
	Double						deviationCostProjects;
	Double						minimumCostProjects;
	Double						maximumCostProjects;
}
