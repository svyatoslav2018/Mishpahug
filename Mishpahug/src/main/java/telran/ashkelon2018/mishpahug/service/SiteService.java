package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDateTime;

import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.dto.DatePeriodDto;
import telran.ashkelon2018.mishpahug.dto.NewEventDto;

public interface SiteService {// not finished

	Event addNewEvent(NewEventDto newEvent);

	Event getEvent(String email, LocalDateTime dateCreated);// id = email+dateCreated

	Event removeEvent(String email, LocalDateTime dateCreated, String token);// id = email+dateCreated

	boolean addRating(String email, LocalDateTime dateCreated);//id=email+dateCreated
	
	//boolean addRating(EventIDDto eventIDDto);

	Iterable<Event> findEventsByCity(String city);

	Iterable<Event> findEventsByDates(DatePeriodDto periodDto);

	Iterable<Event> findEventsByHoliday(String holiday);

	Iterable<Event> findEventsByConfession(String confession);

	Iterable<Event> findEventsByFoodPref(String foodPreference);

}
