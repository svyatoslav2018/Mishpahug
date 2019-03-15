package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateFrom;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateTo;
	String holidays;
	String confession;
	String food;
}
