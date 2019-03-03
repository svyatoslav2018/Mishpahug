package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Document(collection = "Events")
@Builder

public class Event {

	@Id
	String eventId;
	String owner; 
	//Integer age;// age of the owner of event=localDateNow-dateOfBirth
	@Setter String title;
	@Setter String holiday;
	@Setter Address address; 
	@Setter String confession;


//	@JsonFormat(pattern = "yyyy-MM-dd")
//	 LocalDate date;
//	@JsonFormat(pattern = "HH:mm:ss Z")
//	LocalTime time;
	LocalDateTime dateTimeCreation;
	Integer duration;// in minutes
	@Setter String food;
	@Setter String description;
	
}