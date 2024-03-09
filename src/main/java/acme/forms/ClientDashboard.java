
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

	int[]						progressLogsCounter;

	Double						contractBudgetAverage;
	Double						contractBudgetDeviation;
	Double						minimumBudget;
	Double						maximumBudget;

}
