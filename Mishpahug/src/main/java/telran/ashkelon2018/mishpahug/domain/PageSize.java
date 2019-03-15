package telran.ashkelon2018.mishpahug.domain;

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
@ToString
@Builder

public class PageSize {

	Integer totalElements;
	Integer totalPages;
	Integer size;
	Integer number;
	Integer numberOfElements;
	Boolean first;
	Boolean last;
	EventsSort sort;

}