
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditType;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService<Auditor, CodeAudit> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int auditorId;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		codeAudit = this.repository.findOneCodeAuditById(id);
		status = auditorId == codeAudit.getAuditor().getId();

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
	public void unbind(final CodeAudit object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices projects;
		Dataset dataset;
		String modeMark;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		modeMark = EnumMode.mode(marks);
		Collection<Project> unpublishedProjects = this.repository.findAllUnpublishedProjects();
		projects = SelectChoices.from(unpublishedProjects, "title", object.getProject());
		choices = SelectChoices.from(AuditType.class, object.getType());

		dataset = super.unbind(object, "code", "published", "execution", "type", "correctiveActions", "link");
		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);
		dataset.put("auditTypes", choices);
		dataset.put("modeMark", modeMark);

		super.getResponse().addData(dataset);
	}

}
