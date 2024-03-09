
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	private int[]				progressLogsCounter;

	private Double				contractBudgetAverage;
	private Double				contractBudgetDeviation;
	private Double				minimumBudget;
	private Double				maximumBudget;

}
