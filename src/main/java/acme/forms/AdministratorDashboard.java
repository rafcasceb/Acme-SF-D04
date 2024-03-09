
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

	private Double				avgValueInRisks;
	private Double				stanDevValueInRisks;
	private Double				maximumValueInRisks;
	private Double				minimumValueInRisks;

	//TODO: average, minimum, maximum, and standard deviation of the number of claims posted over the last 10 weeks
	private Double				avgNumberOfClaimsLastTenWeeks;
	private Double				stanDevNumberOfClaimsLastTenWeeks;
	private Integer				maximumNumberOfClaimsLastTenWeeks;
	private Integer				minimumNumberOfClaimsLastTenWeeks;

}
