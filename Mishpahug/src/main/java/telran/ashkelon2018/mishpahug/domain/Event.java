package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
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
@Document(collection = "Eventsandhugs")
@Builder

public class Event {
	@Id
	String eventId;
	String owner;
	@Setter String title;
	@Setter String holiday;
	@Setter Address address; 
	@Setter Location location;
	@Setter String confession;
	LocalDate date;
	LocalTime time;
	Integer duration;// in minutes
	@Setter String food;
	@Setter String description;
	@Setter String eventStatus;
	
}