package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = { "email" })
@Document(collection = "Mishpahug_users")

public class UserAccount {
	@Id
	String email;
	String password;
	
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
