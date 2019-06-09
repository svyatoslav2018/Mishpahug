package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;

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
public class HistoryListDto {
	
	String eventId;
	String title;
	String holiday;
	String confession;
	LocalDate date;
	String food;
	String description;
	String eventStatus;//In progress, Done, Pending, Not done
	

}
