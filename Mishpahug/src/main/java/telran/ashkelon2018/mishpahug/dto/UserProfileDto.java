package telran.ashkelon2018.mishpahug.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserProfileDto {
	
	String firstName;
	String lastName;
	String dateOfBirth;
	String gender;
	String maritalStatus;
	String confession;
	String[] pictureLink;// avatar and banner
	String phoneNumber;
	String[] foodPreferences;
	String[] languages;
	String description;
	Double rate;
	Integer numberOfVoters;
	Set<String> roles;
}
