package telran.ashkelon2018.mishpahug.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaticFieldsDto {

	Map<Integer, String> confession;
	
	Map<Integer, String> gender;
	
	Map<Integer, String> maritalStatus;

	Map<Integer, String> foodPreferences;

	Map<Integer, String> languages;

	Map<Integer, String> holiday;

}
