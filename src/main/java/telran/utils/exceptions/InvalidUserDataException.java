package telran.utils.exceptions;

import java.io.Serial;

public class InvalidUserDataException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4212025244364492076L;
	public InvalidUserDataException(String message) {
        super(message);
    }
}
