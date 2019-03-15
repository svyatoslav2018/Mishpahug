package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = { "eventId" })
@ToString
@Document(collection = "Events")
@Builder

public class Event {

	@Id
	String eventId;
<<<<<<< HEAD
	String owner; // String owner;
	//Integer age;// age of the owner of event=localDateNow-dateOfBirth
	String title;
	String holiday;
	//Map<String, String> address;// keys: city, place_id, location(coordinates)
//	String city;
//	String place_id;
//	String[] location;
	Address address;
	String confession;

//	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDateTime dateTimeCreation;

//@JsonFormat(pattern = "HH:mm'T'aa") // aa set AM PM
//	LocalTime time;
=======
	String owner;
	@Setter String title;
	@Setter String holiday;
	@Setter Address address; 
	@Setter Location location;
	@Setter String confession;
	LocalDate date;
	LocalTime time;
	//LocalDateTime localDateTimeEvent;
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git
	Integer duration;// in minutes
<<<<<<< HEAD
	String food;
	String description;


=======
	@Setter String food;
	@Setter String description;
	@Setter String eventStatus;
	PageSize pageSize;
	
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git
}