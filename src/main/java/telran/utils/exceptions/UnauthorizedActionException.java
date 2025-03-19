package telran.utils.exceptions;

import java.io.Serial;

public class UnauthorizedActionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 853524064109157764L;

	public UnauthorizedActionException(String message) {
	        super(message);
	    }
}
