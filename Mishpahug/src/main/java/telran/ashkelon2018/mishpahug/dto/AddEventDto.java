package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.mishpahug.domain.Address;
import telran.ashkelon2018.mishpahug.domain.UserAccount;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AddEventDto {
	
	String eventId;
	String owner;
	String title;
	String holiday;
	Address address;
	String confession;
	LocalDate date;
	LocalTime time;
	//LocalDateTime localDateTimeEvent;
	Integer duration;//in minutes
	String food;
	String description;
	String eventStatus;//In progress, Done, Pending, Not done
	

}
