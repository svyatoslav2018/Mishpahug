package telran.ashkelon2018.mishpahug.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)

public class UnqualifiedLoginOrPassword extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
