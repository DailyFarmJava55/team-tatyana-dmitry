package telran.auth.account.dto.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -4295194319482629579L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
