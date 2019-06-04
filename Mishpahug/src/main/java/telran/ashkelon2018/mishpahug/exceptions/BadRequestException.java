package telran.ashkelon2018.mishpahug.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter

public class BadRequestException extends RuntimeException {
	int code;
	String message;

	public BadRequestException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
