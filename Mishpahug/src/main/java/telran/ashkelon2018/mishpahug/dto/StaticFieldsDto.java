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
	// confession.put(0, "religious");
	// confession.put(1, "irreligious");
	Map<Integer, String> gender;
	// gender.put(0, "male");
	// gender.put(1, "female");
	Map<Integer, String> maritalStatus;
	// maritalStatus.put(0, "married");
	// maritalStatus.put(1, "single");
	// maritalStatus.put(2, "couple");
	Map<Integer, String> foodPreferences;
	// foodPreferences.put(0, "vegetarian");
	// foodPreferences.put(1, "kosher");
	// foodPreferences.put(2, "non-vegetarian");
	Map<Integer, String> languages;
	// languages.put(0, "Hebrew");
	// languages.put(1, "English");
	// languages.put(2, "Russian");
	Map<Integer, String> holiday;
	// holiday.put(0, "Pesah");
	// holiday.put(1, "Shabbat");
	// holiday.put(2, "Other");

}
