package telran.utils.exceptions;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4295194319482629579L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
