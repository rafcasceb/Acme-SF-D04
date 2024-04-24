
package acme.features.authenticated.moneyExchange;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.forms.MoneyExchange;

@Controller
public class AuthenticatedMoneyExchangeController extends AbstractController<Authenticated, MoneyExchange> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedMoneyExchangePerformService exchangeService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("perform", this.exchangeService);
	}

}
