package telran.ashkelon2018.mishpahug.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class EventListResponseDto {
	

	Integer page;
	Integer size;
	List<AddEventDto> content;

	public EventListResponseDto(List<AddEventDto> content, Integer page, Integer size) {// , Integer page, Integer size
		this.content = content;
		 this.page=page;
		 this.size=size;
	}

}
