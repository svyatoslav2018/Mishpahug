package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class NewEventDto {
	
	String title;
	String holiday;
	//Map<String,Set<String>> address;// keys: city, place_id, location(coordinates)
	String city;
	String place_id;
	String[] location;
	String confession;
	LocalDate date;
	LocalTime time;
	Integer duration;//in minutes
	String[] food;
	String description;

}
