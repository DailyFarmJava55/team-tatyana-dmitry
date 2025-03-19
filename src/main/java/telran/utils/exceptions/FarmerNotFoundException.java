package telran.utils.exceptions;

import java.io.Serial;

public class FarmerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2819391507728452198L;
	public FarmerNotFoundException(String message) {
        super(message);
    }

}
