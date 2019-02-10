
package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;

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
@Setter
@Getter
@EqualsAndHashCode(of = { "email" })
@Document(collection = "Mishpahug_users")
@Builder
public class UserAccount {
	@Id
	String email;
	String password;
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

}

