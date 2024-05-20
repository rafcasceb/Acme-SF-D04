
package acme.components.moneyExchange;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.moneyExchange.MoneyExchange;

@Repository
public interface MoneyExchangeRepository extends AbstractRepository {

	@Query("select c.defaultCurrency from Configuration c")
	String findDefaultCurrency();

	@Query("select me from MoneyExchange me where me.sourceCurrency = :sourceCurrency")
	MoneyExchange findLastMoneyExchangeForCurrency(String sourceCurrency);

}
