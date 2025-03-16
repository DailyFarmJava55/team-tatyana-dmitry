package telran.utils.exceptions;

public class SurpriseBoxNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5750346938756747171L;

	public SurpriseBoxNotFoundException(String message) {
        super(message);
    }
}
