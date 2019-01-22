package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class NewEventDto {
	String title;
	String holiday;
	String address;// use google autocomplete validation
//	LocalDate dateFrom;// use standart date picker
//	LocalDate dateTo;
	LocalDateTime dateTimeFrom;// use standart date picker
	LocalDateTime dateTimeTo;
	String eventConfession;
	String foodPreference;
	String aboutEvent;

}
