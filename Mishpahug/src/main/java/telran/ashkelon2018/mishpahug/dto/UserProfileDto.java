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
	int phoneNumber;
	Set<String> userConfession;
	LocalDate dateOfBirth;
	Set<String> maritalStatus;
	Set<String> foodPreference;
	Set<String> gender;
	Set<String> languages;
	String aboutYourself;

	// Set<String> roles;
}
