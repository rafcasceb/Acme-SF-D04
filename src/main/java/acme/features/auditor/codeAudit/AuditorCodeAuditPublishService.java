
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.EnumMode;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.AuditType;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService<Auditor, CodeAudit> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Auditor auditor;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(id);

		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && !codeAudit.isPublished() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "publish");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		String modeMark;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		modeMark = EnumMode.mode(marks);

		if (!super.getBuffer().getErrors().hasErrors("modeMark"))
			if (modeMark != null) {
				boolean isMarkAtLeastC = modeMark.equals("C") || modeMark.equals("B") || modeMark.equals("A") || modeMark.equals("A_PLUS");
				super.state(isMarkAtLeastC, "modeMark", "validation.codeaudit.mode.less-than-c");
			} else
				super.state(false, "modeMark", "validation.codeaudit.mode.less-than-c");

		Collection<AuditRecord> auditRecords;

		auditRecords = this.repository.findManyAuditRecordsByCodeAuditId(object.getId());

		super.state(auditRecords.stream().allMatch(AuditRecord::isPublished), "*", "validation.codeaudit.publish.unpublished-audit-records");
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices projects;
		Dataset dataset;
		String modeMark;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		modeMark = EnumMode.mode(marks);

		Collection<Project> allProjects = this.repository.findAllProjects();
		projects = SelectChoices.from(allProjects, "code", object.getProject());
		choices = SelectChoices.from(AuditType.class, object.getType());

		dataset = super.unbind(object, "code", "published", "execution", "type", "correctiveActions", "link");
		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);
		dataset.put("auditTypes", choices);
		dataset.put("modeMark", modeMark);

		super.getResponse().addData(dataset);
	}

}
