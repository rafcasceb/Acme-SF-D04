
package acme.features.administrator.risk;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.SpamDetector;
import acme.entities.configuration.Configuration;
import acme.entities.risks.Risk;

@Service
public class AdministratorRiskUpdateService extends AbstractService<Administrator, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorRiskRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Risk risk;

		masterId = super.getRequest().getData("id", int.class);
		risk = this.repository.findOneRiskById(masterId);
		status = risk != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Risk object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneRiskById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Risk object) {
		assert object != null;

		super.bind(object, "reference", "identificationDate", "impact", "probability", "description", "link");
	}

	@Override
	public void validate(final Risk object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("reference")) {
			Risk riskSameReference;
			Boolean isReferenceUsedByAnotherRisk;

			riskSameReference = this.repository.findRiskByReference(object.getReference());
			isReferenceUsedByAnotherRisk = riskSameReference != null && riskSameReference.getId() != object.getId();
			super.state(!isReferenceUsedByAnotherRisk, "reference", "administrator.risk.form.error.duplicate");
		}

		if (!super.getBuffer().getErrors().hasErrors("identificationDate")) {
			Date minimumDate;

			minimumDate = MomentHelper.parse("01/01/2000", "dd/MM/yyyy");
			super.state(MomentHelper.isAfterOrEqual(object.getIdentificationDate(), minimumDate), "identificationDate", "administrator.risk.form.error.identification-date");
		}

		if (!super.getBuffer().getErrors().hasErrors("impact"))
			super.state(object.getImpact() >= 0.0 && object.getImpact() <= 1.0, "impact", "administrator.risk.form.error.impact");

		if (!super.getBuffer().getErrors().hasErrors("probability"))
			super.state(object.getProbability() >= 0.0 && object.getProbability() <= 1.0, "probability", "administrator.risk.form.error.probability");

		if (!super.getBuffer().getErrors().hasErrors("description")) {
			Configuration config = this.repository.findConfiguration();
			String spamTerms = config.getSpamTerms();
			Double spamThreshold = config.getSpamThreshold();
			SpamDetector spamHelper = new SpamDetector(spamTerms, spamThreshold);
			super.state(!spamHelper.isSpam(object.getDescription()), "description", "administrator.risk.form.error.spam");
		}
	}

	@Override
	public void perform(final Risk object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "description", "link");
		dataset.put("derivedValue", object.getValue());

		super.getResponse().addData(dataset);
	}

}
