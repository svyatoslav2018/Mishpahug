package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MyEventsToResp {
	String eventId;
	String title;
	String holiday;
	String confession;
	LocalDate date;
	LocalTime time;
	Integer duration;//in minutes
	String food;
	String description;
	String eventStatus;
	List<ParticipantsDto> participants;
	

}
