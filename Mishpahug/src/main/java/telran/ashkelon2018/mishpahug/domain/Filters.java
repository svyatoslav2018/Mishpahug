package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Filters {
	LocalDate dateFrom;
	LocalDate dateTo;
	String holidays;
	String confession;
	String food;
}
