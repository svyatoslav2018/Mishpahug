package telran.ashkelon2018.mishpahug.dto;

import java.util.List;

import org.springframework.data.domain.Sort;

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
	Integer totalElements;
	Integer totalPages;
	Integer number;// number of page
	Integer numberOfElements;// real quantity of elements on the page
	Boolean first;
	Boolean last;
	Sort sort;

	Integer page;
	Integer size;
	List<AddEventDto> content;

	public EventListResponseDto(List<AddEventDto> content, Integer page, Integer size) {
		this.content = content;
		this.size = size;
		this.totalElements = content.size();
		this.totalPages = content.size() % size > 0 ? (content.size() / size) + 1 : content.size() / size;
		this.numberOfElements = (number >= this.totalPages ? true : false) ? content.size() % size : size;
		this.first = number == 0 ? true : false;
		this.last = number >= this.totalPages ? true : false;
		this.sort = new Sort(Sort.Direction.DESC, "dateFrom");
	}

}
