
package acme.components;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRate {

	// Attributes -------------------------------------------------------------

	public boolean				success;
	public String				terms;
	public String				privacy;
	public long					timestamp;
	public String				source;
	public Map<String, Double>	quotes;

}
