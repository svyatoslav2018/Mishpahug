package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.mishpahug.domain.Address;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class EventListResponseDto {
	
	public EventListResponseDto(List<EventDto> collect, Integer page, Integer size) {
		// TODO Auto-generated constructor stub
		
	}
	String owner;
	String title;
	String holiday;
	Address address;
	String confession;
	LocalDate date;
	LocalTime time;
	Integer duration;//in minutes
	String food;
	String description;
	String status;//In progress, Done, Pending, Not done

}
