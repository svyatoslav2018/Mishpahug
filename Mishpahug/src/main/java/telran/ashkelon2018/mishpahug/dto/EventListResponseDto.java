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

// 	public EventListResponseDto(List<AddEventDto> content, Integer page, Integer size) {// , Integer page, Integer size
// 		this.content = content;
// 		 this.page=page;
// 		 this.size=size;
// 	}

//}

