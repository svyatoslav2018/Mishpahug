package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class AddEvent {
	String title;
	String holiday;
	String address;// use google autocomplete validation
	LocalDate dateFrom;// use standart date picker
	LocalDate dateTo;
	LocalDateTime timeFrom;
	LocalDateTime timeTo;
	String confession;
	String foodPreference;
	String aboutEvent;

}
