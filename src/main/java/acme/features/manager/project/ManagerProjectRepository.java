
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
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

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findManyProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.code = :code")
	Project findProjectByCode(String code);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select us from UserStory us where us.project.id = :projectId")
	Collection<UserStory> findManyUserStoriesByProjectId(int projectId);

	@Query("select ca from CodeAudit ca where ca.project.id = :projectId")
	Collection<CodeAudit> findManyCodeAuditsByProjectId(int projectId);

	@Query("select ar from AuditRecord ar where ar.audit.id = :auditId")
	Collection<AuditRecord> findManyAuditRecordsByAuditId(int auditId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findManyContractsByProjectId(int projectId);

	@Query("select pl from ProgressLog pl where pl.contract.id = :contractId")
	Collection<ProgressLog> findManyProgressLogsByContractId(int contractId);

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findManySponsorshipsByProjectId(int projectId);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findManyInvoicesBySponsorshipId(int sponsorshipId);

	@Query("select tm from TrainingModule tm where tm.project.id = :projectId")
	Collection<TrainingModule> findManyTrainingModulesByProjectId(int projectId);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :trainingModuleId")
	Collection<TrainingSession> findManyTrainingSessionsByTrainingModuleId(int trainingModuleId);

}
