package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.tomcat.jni.Local;
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
	String owner; 
	//Integer age;// age of the owner of event=localDateNow-dateOfBirth
	@Setter String title;
	@Setter String holiday;
	@Setter Address address; 
	@Setter String confession;
	LocalDate date;
	LocalTime time;
	//LocalDateTime localDateTimeEvent;
	Integer duration;// in minutes
	@Setter String food;
	@Setter String description;
	@Setter String eventStatus;
	PageSize pageSize;
	
}