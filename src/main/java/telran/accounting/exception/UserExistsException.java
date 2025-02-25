package telran.accounting.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User already exists");
    }

    public UserExistsException(String message) {
        super(message);
    }
}