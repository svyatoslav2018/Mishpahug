package telran.ashkelon2018.mishpahug.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.mishpahug.domain.Filters;
import telran.ashkelon2018.mishpahug.domain.Location;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class EventListRequestDto {
	
	Location location;
	Filters filters;
	
}
