package telran.utils.exceptions;

public class UnauthorizedActionException extends RuntimeException {
	private static final long serialVersionUID = 853524064109157764L;

	public UnauthorizedActionException(String message) {
	        super(message);
	    }
}
