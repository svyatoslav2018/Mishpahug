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
@Getter
@Setter
@Builder
@ToString

public class EventListResponseDto {
	List<FullEvent2Resp> content;
	long totalElements;
	int totalPages;
	int size;
	int number;
	int numberOfElements;
	boolean first;
	boolean last;
	Sort sort;
}

// Integer page;
// Integer size;
// public EventListResponseDto(List<FullEvent2Resp> content, Integer page,
// Integer size) {
// this.content = content;
// this.size = size;
// this.totalElements = content.size();
// this.totalPages = content.size() % size > 0 ? (content.size() / size) + 1 :
// content.size() / size;
// this.numberOfElements = (number >= this.totalPages ? true : false) ?
// content.size() % size : size;
// this.first = number == 0 ? true : false;
// this.last = number >= this.totalPages ? true : false;
// this.sort = new Sort(Sort.Direction.ASC, "dateFrom");
// }
// }
