
package acme.features.client;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.contracts.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract object;
		Client client;
		Date instantiationMoment;
		instantiationMoment = MomentHelper.getCurrentMoment();

		client = this.repository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Contract();
		object.setClient(client);
		object.setInstantiationMoment(instantiationMoment);
		object.setPublished(false);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;
		Project p = this.repository.findOneProjectById(109);

		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		// object.setProject(p);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract contractSameCode;
			contractSameCode = this.repository.findContractByCode(object.getCode());
			super.state(contractSameCode == null, "code", "client.contract.form.error.duplicate");
		}
		//TODO: Ver si la validacion del budget va en el create o en publish
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "published", "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");

		super.getResponse().addData(dataset);
	}

}
