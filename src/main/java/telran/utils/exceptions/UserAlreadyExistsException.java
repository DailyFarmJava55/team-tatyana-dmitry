package telran.utils.exceptions;

import java.io.Serial;

public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 837424255455149953L;
	public UserAlreadyExistsException(String message) {
        super(message);
    }
}
