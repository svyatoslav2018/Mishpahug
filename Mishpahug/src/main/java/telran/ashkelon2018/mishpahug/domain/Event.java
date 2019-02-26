package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import telran.ashkelon2018.mishpahug.dao.SiteRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;

@NoArgsConstructor
// @AllArgsConstructor
@Getter
@EqualsAndHashCode(of = { "eventId" })
@ToString
@Setter
@Document(collection = "Events")
// @Builder
public class Event {

	@Autowired
	SiteRepository siteRepository;
	
	@Autowired
	UserAccountRepository userRepository;

	
	String login;
	//String owner;
	@Id
	Integer eventId;
	Integer age;// age of the owner of event=localDateNow-dateOfBirth
	String title;
	String holiday;
	Map<String, String> address;// keys: city, place_id, location(coordinates)
	String eventConfession;
	Integer duration;// in minutes
	String[] foodPreference;
	String description;

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate date;

	@JsonFormat(pattern = "HH:mm'T'aa") // aa set AM PM
	LocalTime time;

	public Event(String title, String holiday, Map<String, String> address, String eventConfession, LocalDate date,
			LocalTime time, Integer duration, String[] foodPreference, String description) {
		this.title = title;
		this.holiday = holiday;
		this.address = address;
		this.eventConfession = eventConfession;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.foodPreference = foodPreference;
		this.description = description;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
		LocalDate dateNow = LocalDate.now();
		String formattedDateTime = dateNow.format(formatter);
		System.out.println(formattedDateTime);
		//String eventIdStr;
		// eventId.toString();
		UserAccount userAccount=userRepository.findById(login).get();
		//owner = userRepository.findById(login).get();
		age = Integer.parseInt(formattedDateTime) - Integer.parseInt(userAccount.getDateOfBirth());
		System.out.println(age);
//		for (int j = 1; j<Integer.MAX_VALUE ; j++) {
//			eventId=j;
//			j++;
//			break;
//		}

	}
}