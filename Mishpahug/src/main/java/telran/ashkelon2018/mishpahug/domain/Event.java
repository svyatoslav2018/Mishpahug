package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"email","dateCreated"})//email(eventOwner)+event_dateCreated
@ToString

//@Document(collection="ForumFilter")

public class Event {//not finished!!!
	
	String email;
	@Setter String title;
	@Setter String holiday;
	String address;
	String eventConfession;
	String typeOfKitchen;
	String aboutEvent;
	int rates;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime dateCreated;

	//http://www.java2s.com/Tutorials/Java/Java_Format/0030__Java_Date_Format_Symbol.htm
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'T'aa")//aa set AM PM
	LocalDateTime from;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'T'aa")//aa set AM PM
	LocalDateTime to;
	
	
	
	public void addRating() {
		rates++;
	}

}