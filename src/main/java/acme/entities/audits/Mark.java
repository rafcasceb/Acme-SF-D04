
package acme.entities.audits;

public enum Mark {

	A_PLUS, A, B, C, F, F_MINUS;


	@Override
	public String toString() {
		switch (this) {
		case A_PLUS:
			return "A+";
		case F_MINUS:
			return "F-";
		default:
			return super.toString();
		}
	}

}
