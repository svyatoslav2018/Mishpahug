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
	import telran.ashkelon2018.mishpahug.domain.EventOwner;

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Getter
	@Setter
	@ToString
	public class SubscribedEventToResp {
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
		String eventStatus;
		EventOwner owner;
}
