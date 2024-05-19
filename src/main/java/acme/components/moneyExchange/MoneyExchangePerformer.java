
package acme.components.moneyExchange;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;
import acme.entities.moneyExchange.MoneyExchange;

@Component
public class MoneyExchangePerformer {

	@Autowired
	private MoneyExchangeRepository repository;


	public Money performMoneyExchangeToDefault(final Money userMoney) {
		Money exchangeResult;
		String defaultCurrency = this.repository.findDefaultCurrency();

		if (userMoney.getCurrency().equals(defaultCurrency))
			exchangeResult = userMoney;
		else
			exchangeResult = this.calculateMoneyExchange(userMoney, defaultCurrency);

		return exchangeResult;
	}

	private Money calculateMoneyExchange(final Money userMoney, final String defaultCurrency) {
		Money exchangeResult;
		MoneyExchange lastMoneyExchange;
		Date currentDate;
		Double ratio;

		lastMoneyExchange = this.repository.findLastMoneyExchangeForCurrency(userMoney.getCurrency());
		currentDate = MomentHelper.getCurrentMoment();

		if (lastMoneyExchange != null && MomentHelper.isEqual(currentDate, lastMoneyExchange.getDate()) && defaultCurrency.equals(lastMoneyExchange.getDefaultCurrency()))
			ratio = lastMoneyExchange.getRatio();
		else
			ratio = this.obtainNewMoneyExchangeRatio(userMoney.getCurrency(), defaultCurrency);

		if (ratio != null) {
			exchangeResult = new Money();
			exchangeResult.setCurrency(defaultCurrency);
			exchangeResult.setAmount(userMoney.getAmount() * ratio);
		} else
			exchangeResult = null;

		return exchangeResult;
	}

	private Double obtainNewMoneyExchangeRatio(final String sourceCurrency, final String targetCurrency) {
		assert !StringHelper.isBlank(sourceCurrency);
		assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange result;
		RestTemplate api;
		ExchangeRate recordResponse;
		Double ratio;
		Date currentDate;

		try {
			api = new RestTemplate();

			recordResponse = api.getForObject( //				
				"https://api.currencyapi.com/v3/latest?apikey={0}&base_currency={1}&currencies={2}", //
				ExchangeRate.class, //
				"cur_live_9CS0QA54yYzg4W3iJ1QQMMktAfPY2DpLVpUjpPKP", //
				sourceCurrency, //
				targetCurrency);

			assert recordResponse != null;
			ratio = Double.valueOf(recordResponse.getData().get(targetCurrency).get("value"));

			currentDate = MomentHelper.getCurrentMoment();
			result = new MoneyExchange();
			result.setSourceCurrency(sourceCurrency);
			result.setDefaultCurrency(targetCurrency);
			result.setDate(currentDate);
			result.setRatio(ratio);

			this.storeLastExchangeRatios(result);

			MomentHelper.sleep(1000); // HINT: need to pause the requests to the API a bit down to prevent DOS attacks
		} catch (final Throwable oops) {
			ratio = null;
		}

		return ratio;
	}

	private void storeLastExchangeRatios(final MoneyExchange object) {
		assert object != null;

		MoneyExchange lastExchange = this.repository.findLastMoneyExchangeForCurrency(object.getSourceCurrency());
		if (lastExchange != null)
			this.repository.deleteById(lastExchange.getId());

		this.repository.save(object);
	}

}
