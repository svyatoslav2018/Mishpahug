package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalTime;

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

public class EventCalendar {
	String eventId;
	String title;
	LocalDate date;
	LocalTime time;
	Integer duration;// in minutes
	String eventStatus;
}


//"eventId": 2,
//"title": "Celebration of Pesah ending",
//"date": "2018-03-24",
//"time": "18:32:00",
//"duration": 180,
//"status": "Pending"