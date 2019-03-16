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
//	String eventId;
//	String title;
//	String holiday;
//	String confession;
//	LocalDate date;
//	LocalTime time;
//	Integer duration;// in minutes
//	Address address;
//	String food;
//	String description;
//	
	List<AddEventDto> collect;
	Integer page;
	Integer size;
	

//	public EventListResponseDto(List<AddEventDto> collect, Integer page, Integer size) {// , Integer page, Integer size
//		this.collect = collect;
//		 this.page=page;
//		 this.size=size;
//	}

}
