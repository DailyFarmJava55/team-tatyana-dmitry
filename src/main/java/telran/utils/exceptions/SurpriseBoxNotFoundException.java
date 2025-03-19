package telran.utils.exceptions;

import java.io.Serial;

public class SurpriseBoxNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5750346938756747171L;

	public SurpriseBoxNotFoundException(String message) {
        super(message);
    }
}
