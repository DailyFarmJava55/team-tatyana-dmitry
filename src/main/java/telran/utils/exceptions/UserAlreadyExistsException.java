package telran.utils.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 837424255455149953L;
	public UserAlreadyExistsException(String message) {
        super(message);
    }
}
