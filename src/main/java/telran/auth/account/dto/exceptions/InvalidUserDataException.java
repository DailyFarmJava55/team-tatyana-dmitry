package telran.auth.account.dto.exceptions;

public class InvalidUserDataException extends RuntimeException {

	private static final long serialVersionUID = -4212025244364492076L;
	public InvalidUserDataException(String message) {
        super(message);
    }
}
