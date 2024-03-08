
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.entities.audits.AuditType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Map<AuditType, Integer>	totalAuditTypes;

	private Double					averageAuditRecords;
	private Double					deviationAuditRecords;
	private Integer					minimumAuditRecords;
	private Integer					maximumAuditRecords;

	private Double					averageRecordPeriod;
	private Double					deviationRecordPeriod;
	private Double					minimumRecordPeriod;
	private Double					maximumRecordPeriod;
}
