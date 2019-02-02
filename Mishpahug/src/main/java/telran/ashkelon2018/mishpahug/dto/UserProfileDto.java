package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
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
public class UserProfileDto {
	String firstName;
	String lastName;
	LocalDate dateOfBirth;
	Map<Integer, String> gender;
	Map<Integer, String> maritalStatus;
	Map<Integer, String> confession;
	String[] pictureLink;// avatar and banner
	String phoneNumber;
	Map<Integer, String> foodPreferences;
	Map<Integer, String> languages;
	String description;
	Double rate;
	Integer numberOfVoters;

}
