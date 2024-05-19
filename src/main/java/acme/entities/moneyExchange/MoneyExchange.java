
package acme.entities.moneyExchange;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MoneyExchange extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{3}")
	private String				sourceCurrency;

	@NotBlank
	@Pattern(regexp = "[A-Z]{3}")
	private String				defaultCurrency;

	@NotNull
	private Date				date;

	private double				ratio;

}
