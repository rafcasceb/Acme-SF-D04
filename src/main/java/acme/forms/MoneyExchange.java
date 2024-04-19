
package acme.forms;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyExchange extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Query attributes -------------------------------------------------------

	@NotNull
	@Valid
	public Money				source;

	@NotBlank
	@Pattern(regexp = "[A-Z]{3}")
	public String				targetCurrency;

	// Response attributes ----------------------------------------------------

	@Valid
	public Money				target;

	public Date					date;

}
