package telran.ashkelon2018.mishpahug.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Address {
	String city;
	String place_id;
	Location location;
}