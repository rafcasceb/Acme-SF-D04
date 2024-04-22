
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select p from ProgressLog p where p.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select p from ProgressLog p where p.contract.id = :contractId")
	Collection<ProgressLog> findManyProgressLogsByContractId(int contractId);

	@Query("select p from ProgressLog p where p.id = :id")
	Collection<ProgressLog> findManyProgressLogsById(int id);

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select p from ProgressLog p where p.recordId = :recordId")
	ProgressLog findProgressLogByRecordId(String recordId);

	@Query("select p from ProgressLog p where p.recordId = :recordId and p.id != :id")
	ProgressLog findProgressLogByRecordIdDifferentId(String recordId, int id);

	@Query("select c from Contract c where c.published = false")
	Collection<Contract> findAllUnpublishedContracts();

	@Query("select p from ProgressLog p where p.contract.client.id = :clientId")
	Collection<ProgressLog> findManyProgressLogsByClientId(int clientId);

}
