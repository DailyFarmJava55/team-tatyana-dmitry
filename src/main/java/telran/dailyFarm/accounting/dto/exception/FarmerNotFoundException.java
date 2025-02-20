package telran.dailyFarm.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FarmerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4197606616662184394L;

}
