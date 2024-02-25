
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private int					totalStaticAudits;
	private int					totalDynamicAudits;

	private double				averageAuditRecords;
	private double				deviationAuditRecords;
	private int					minimumAuditRecords;
	private int					maximumAuditRecords;

	private double				averageRecordPeriod;
	private double				deviationRecordPeriod;
	private double				minimumRecordPeriod;
	private double				maximumRecordPeriod;
}
