package telran.ashkelon2018.mishpahug.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class OwnerDto {

	String fullName;
	String confession;
	String gender;
	Integer age;
	String[] pictureLink;// avatar and banner
	String maritalStatus;
	String[] foodPreferences;
	String[] languages;
	Double rate;
}
