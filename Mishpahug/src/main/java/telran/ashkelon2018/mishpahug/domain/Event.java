package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@EqualsAndHashCode(of = { "owner" })
@ToString
@Setter
@Document(collection = "Events")
@Builder

public class Event {

	@Id
	String eventId;
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
	LocalDate date;

//@JsonFormat(pattern = "HH:mm'T'aa") // aa set AM PM
	LocalTime time;
	Integer duration;// in minutes
	String food;
	String description;


}