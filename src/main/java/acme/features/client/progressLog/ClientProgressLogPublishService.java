
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
public class ClientProgressLogPublishService extends AbstractService<Client, ProgressLog> {

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
		super.bind(object, "publish");
	}

	@Override
	public void validate(final ProgressLog object) {
		assert object != null;

	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
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
