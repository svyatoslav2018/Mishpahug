
package telran.ashkelon2018.mishpahug.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Address {
	String city;
	String place_id;
	Location location;
	public Address(String city){
		this.city=city;
	}
}