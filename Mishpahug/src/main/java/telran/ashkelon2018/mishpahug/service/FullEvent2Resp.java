package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.mishpahug.domain.Address;
import telran.ashkelon2018.mishpahug.domain.EventOwner;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FullEvent2Resp {
	
	String eventId;
	String title;
	String holiday;
	String confession;
	//@JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date;
	@JsonFormat(pattern = "hh:mm")
	LocalTime time;
	//LocalDateTime localDateTimeEvent;
	Integer duration;//in minutes
	Address address;
	String food;
	String description;
	EventOwner owner;

	

}
