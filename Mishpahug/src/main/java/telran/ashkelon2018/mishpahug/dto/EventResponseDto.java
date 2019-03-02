package telran.ashkelon2018.mishpahug.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@ToString
public class EventResponseDto {
	Integer code;
	String message;
}
