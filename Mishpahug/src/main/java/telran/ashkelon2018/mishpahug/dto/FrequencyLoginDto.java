package telran.ashkelon2018.mishpahug.dto;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FrequencyLoginDto {
	int id;
	String user;
	long timestamp;
//	HashMap<String, Long> data;
}