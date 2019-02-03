package telran.ashkelon2018.mishpahug.domain;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "Mishpahug_static")
@Builder
public class StaticFields {
	Map<Integer, String> confession;

	Map<Integer, String> gender;

	Map<Integer, String> maritalStatus;

	Map<Integer, String> foodPreferences;

	Map<Integer, String> languages;

	Map<Integer, String> holiday;

}
