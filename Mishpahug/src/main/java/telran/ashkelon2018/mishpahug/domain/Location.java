package telran.ashkelon2018.mishpahug.domain;

<<<<<<< HEAD
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Location {
	Double lng;
	Double lat;
=======
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Location {
	Double lng;
	Double lat;
	Double radius;
>>>>>>> branch 'master' of https://github.com/svyatoslav2018/Mishpahug_Backend.git

}
