package telran.ashkelon2018.mishpahug.dto;

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
public class ParticipantsDto {
	
	String userId;// subscriberId

	String fullName;
	String confession;
	String gender;
	Integer age;
	String[] pictureLink;// avatar and banner
	String maritalStatus;
	String[] foodPreferences;
	String[] languages;
	Double rate;
	Integer numberOfVoters;

	boolean isInvited;

}
