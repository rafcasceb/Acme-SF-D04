
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.trainingmodule.TrainingModule;
import acme.entities.trainingmodule.TrainingSession;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Project project;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		project = this.repository.findOneProjectById(masterId);
		manager = project == null ? null : project.getManager();
		status = project != null && !project.isPublished() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstractDescription", "fatalErrorPresent", "score", "estimatedCostInHours", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(!object.isPublished(), "published", "manager.project.form.error.already-published");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		Collection<UserStory> userStories;
		Collection<CodeAudit> codeAudits;
		Collection<Contract> contracts;
		Collection<Sponsorship> sponsorships;
		Collection<TrainingModule> trainingModules;

		userStories = this.repository.findManyUserStoriesByProjectId(object.getId());

		codeAudits = this.repository.findManyCodeAuditsByProjectId(object.getId());
		codeAudits.stream().forEach(ca -> {
			Collection<AuditRecord> auditRecords;
			auditRecords = this.repository.findManyAuditRecordsByAuditId(ca.getId());
			this.repository.deleteAll(auditRecords);
		});

		contracts = this.repository.findManyContractsByProjectId(object.getId());
		contracts.stream().forEach(c -> {
			Collection<ProgressLog> progressLogs;
			progressLogs = this.repository.findManyProgressLogsByContractId(c.getId());
			this.repository.deleteAll(progressLogs);
		});

		sponsorships = this.repository.findManySponsorshipsByProjectId(object.getId());
		sponsorships.stream().forEach(s -> {
			Collection<Invoice> invoices;
			invoices = this.repository.findManyInvoicesBySponsorshipId(s.getId());
			this.repository.deleteAll(invoices);
		});

		trainingModules = this.repository.findManyTrainingModulesByProjectId(object.getId());
		trainingModules.stream().forEach(tm -> {
			Collection<TrainingSession> trainingSessions;
			trainingSessions = this.repository.findManyTrainingSessionsByTrainingModuleId(tm.getId());
			this.repository.deleteAll(trainingSessions);
		});

		this.repository.deleteAll(userStories);
		this.repository.deleteAll(codeAudits);
		this.repository.deleteAll(contracts);
		this.repository.deleteAll(sponsorships);
		this.repository.deleteAll(trainingModules);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "published", "code", "title", "abstractDescription", "fatalErrorPresent", "estimatedCostInHours", "link");

		super.getResponse().addData(dataset);
	}

}
