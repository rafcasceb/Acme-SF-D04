
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.contracts.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogDeleteService extends AbstractService<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogRepository repository;

	// AbstractService ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Client client;
		ProgressLog pl;

		id = super.getRequest().getData("id", int.class);
		pl = this.repository.findOneProgressLogById(id);

		client = pl == null ? null : pl.getContract().getClient();
		status = pl != null && !pl.isPublished() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProgressLogById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProgressLog object) {
		assert object != null;
		int contractId;
		Contract contract;

		contractId = super.getRequest().getData("contract", int.class);
		contract = this.repository.findOneContractById(contractId);

		object.setContract(contract);
		super.bind(object, "recordId", "completeness", "comment", "responsiblePerson");

	}

	@Override
	public void validate(final ProgressLog object) {

	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;

		this.repository.delete(object);
	}

	// The unbind method is not override since it will never be called. There is no validation beyond the authorisation.
}
