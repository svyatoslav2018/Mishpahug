package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewEventDto {
	Integer eventId;
	
	String title;
	String holiday;
	Map<String,String> address;// keys: city, place_id, location(coordinates)
	String eventConfession;
	LocalDate date;
	LocalTime time;
	Integer duration;//in minutes
	String[] foodPreference;
	String description;

}
