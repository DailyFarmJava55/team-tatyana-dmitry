package telran.auth.account.dto.exceptions;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -3294233216788359032L;

	public UnauthorizedException(String message) {
		super(message);
	}
}
