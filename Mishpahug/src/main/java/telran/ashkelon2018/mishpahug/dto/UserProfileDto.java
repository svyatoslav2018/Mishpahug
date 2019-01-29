package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.util.Set;

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
	String phoneNumber;
	String userConfession;
	LocalDate dateOfBirth;
	String maritalStatus;
	Set<String> foodPreference;
	String gender;
	Set<String> languages;
	String aboutYourself;
	String[] pictureLink;//avatar and banner

}
