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
import lombok.Singular;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = { "email" })
//@Document(collection = "forum_users")
public class UserAccount {
	@Id
	String email;
	String password;
	String firstName;
	String lastName;
	int phoneNumber;
	String userConfession;
	LocalDate dateOfBirth;
	String maritalStatus;
	String foodPreference;
	String gender;
	String languages;
	String aboutYourself;
	
	@Singular
	Set<String> roles;	

	public void addRole(String role) {
		roles.add(role);

	}

//	public void removeRole(String role) {
//		roles.remove(role);
//
//	}

}