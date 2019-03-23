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


