
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	private int					totalNumberTrainingModulesWithUpdateMoment;
	private int					totalNumberTrainingSessionsWithLink;

	private Double				averageTimeTrainingModules;
	private Double				deviationTimeTrainingModules;
	private Integer				minimumTimeTrainingModules;
	private Integer				maximumTimeTrainingModules;

}
