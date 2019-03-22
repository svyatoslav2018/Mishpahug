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
	int number;// number of page
	int numberOfElements;// real quantity of elements on the page
	boolean first;
	boolean last;
	Sort sort;
}



// package telran.ashkelon2018.mishpahug.dto;

// import java.util.List;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @Getter
// @Setter
// @ToString
// public class EventListResponseDto {
	


// 	Integer page;
// 	Integer size;
// 	List<AddEventDto> content;

// 	public EventListResponseDto(List<AddEventDto> content, Integer page, Integer size) {
// 		this.content = content;
// 		this.size = size;
// 		this.totalElements = content.size();
// 		this.totalPages = content.size() % size > 0 ? (content.size() / size) + 1 : content.size() / size;
// 		this.numberOfElements = (number >= this.totalPages ? true : false) ? content.size() % size : size;
// 		this.first = number == 0 ? true : false;
// 		this.last = number >= this.totalPages ? true : false;
// 		this.sort = new Sort(Sort.Direction.DESC, "dateFrom");
// 	}

// 	public EventListResponseDto(List<AddEventDto> content, Integer page, Integer size) {// , Integer page, Integer size
// 		this.content = content;
// 		 this.page=page;
// 		 this.size=size;
// 	}

//}


