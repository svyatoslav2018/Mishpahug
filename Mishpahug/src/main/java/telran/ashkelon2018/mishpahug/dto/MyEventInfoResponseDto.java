package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.mishpahug.domain.Address;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class MyEventInfoResponseDto {
	String eventId;
	String title;
	String holiday;
	String confession;
	LocalDate date;
	LocalTime time;
	Integer duration;//in minutes
	Address address;
	String food;
	String description;
	String status;
}
