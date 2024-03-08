
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

	private Double				averageAuditRecords;
	private Double				deviationAuditRecords;
	private Integer				minimumAuditRecords;
	private Integer				maximumAuditRecords;

	private Double				averageRecordPeriod;
	private Double				deviationRecordPeriod;
	private Double				minimumRecordPeriod;
	private Double				maximumRecordPeriod;
}
