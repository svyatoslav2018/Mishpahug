package telran.ashkelon2018.mishpahug.domain;

<<<<<<< HEAD
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Address {
	String city;
	String place_id;
	Location location;
}
=======
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
	public Address(String city){
		this.city=city;
	}
}
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git
