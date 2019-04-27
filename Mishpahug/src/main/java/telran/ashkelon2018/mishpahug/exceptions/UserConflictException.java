package telran.ashkelon2018.mishpahug.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
@Setter

public class UserConflictException extends RuntimeException {
	int code;
	String message;

	public UserConflictException(int code, String message) {
		this.code=code;
		this.message=message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CodeResponseDto codeResponseDto = new CodeResponseDto();

}
