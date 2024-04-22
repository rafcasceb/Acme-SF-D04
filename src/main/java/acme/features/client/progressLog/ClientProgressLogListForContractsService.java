
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogListForContractsService extends AbstractService<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogRepository repository;

	// AbstractService<Auditor, AuditRecord> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Client client;
		Contract contract;

		id = super.getRequest().getData("contractId", int.class);
		contract = this.repository.findOneContractById(id);

		client = contract == null ? null : contract.getClient();
		status = contract != null && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<ProgressLog> objects;
		int contractId;

		contractId = super.getRequest().getData("contractId", int.class);
		objects = this.repository.findManyProgressLogsByContractId(contractId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "published");

		super.getResponse().addData(dataset);
	}

}
