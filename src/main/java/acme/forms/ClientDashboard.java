
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

	Integer						progressLogsWithCompletenessBelow25;
	Integer						progressLogsWithCompletenessBetween25And50;
	Integer						progressLogsWithCompletenessBetween50And75;
	Integer						progressLogsWithCompletenessAbove75;

	Double						contractBudgetAverage;
	Double						contractBudgetDeviation;
	Double						minimumBudget;   // Pendiente de Integer
	Double						maximumBudget;   // Pendiente de Integer

}
