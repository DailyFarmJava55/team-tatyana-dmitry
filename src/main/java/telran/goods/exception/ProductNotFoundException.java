package telran.goods.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3405378901538626345L;

	public ProductNotFoundException(String message) {
        super(message);
    }
}
