
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
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
		System.out.print("BIND");
		int contractId;
		Contract contract;

		contractId = super.getRequest().getData("contract", int.class);
		contract = this.repository.findOneContractById(contractId);

		object.setContract(contract);
		super.bind(object, "recordId", "completeness", "comment", "responsiblePerson");
		System.out.print("BIND FINAL");
		System.out.print(object.getRegistrationMoment());

	}

	@Override
	public void validate(final ProgressLog object) {
		System.out.print("VALIDATE");
		assert object != null;
		System.out.println("VALIDADO");

		if (!super.getBuffer().getErrors().hasErrors("contract"))
			super.state(!object.getContract().isPublished(), "contract", "validation.progresslog.published.contract-is-published");

		if (!super.getBuffer().getErrors().hasErrors("published"))
			super.state(!object.isPublished(), "published", "validation.progresslog.published");

	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;
		System.out.print("PERFORM");
		this.repository.delete(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		System.out.println("UNBINF");
		assert object != null;

		SelectChoices contracts;
		Dataset dataset;

		Collection<Contract> unpublishedContracts = this.repository.findAllUnpublishedContracts();
		contracts = SelectChoices.from(unpublishedContracts, "code", object.getContract());

		dataset = super.unbind(object, "recordId", "completeness", "comment", "responsiblePerson", "published");
		dataset.put("contract", contracts.getSelected().getKey());
		dataset.put("contracts", contracts);

		super.getResponse().addData(dataset);
	}

}
