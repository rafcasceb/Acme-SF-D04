
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	// Each role an index and each value the number of roles of that index reference
	private int[]				principalRolesCounter;

	private Double				ratioOfNoticeWithLinkAndEmail;

	private Double				ratioOfObjetiveCritical;
	private Double				ratioOfObjetiveNonCritical;

	private Double				avgValueInRisk;
	private Double				stanDevValueInRisk;
	private Double				maximumValueInRisk;
	private Double				minimumValueInRisk;

	//TODO: average, minimum, maximum, and standard deviation of the number of claims posted over the last 10 weeks
	private Double				avgNumberOfClaims;
	private Double				stanDevNumberOfClaims;
	private Double				maximumNumberOfClaims;
	private Double				minimumNumberOfClaims;

}
