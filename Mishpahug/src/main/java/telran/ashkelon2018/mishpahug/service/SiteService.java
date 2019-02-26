package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.dto.NewEventDto;

public interface SiteService {

	Event addNewEvent(NewEventDto newEvent, String token);

	//Event getEvent(String login, LocalDate date, LocalTime time);// id = login+date+time

	Event removeEvent(String status, String login, LocalDate date, LocalTime time);// status "done" or if owner
																					// canceled

	boolean addRating(String login, LocalDate date, LocalTime time);// id = login+date+time

	Iterable<Event> findAllEvents(String status);// status "In progress"

	Iterable<Event> findEventsByCity(String city);

	Iterable<Event> findEventsByDates(LocalDate dateFrom,LocalDate dateTo);

	Iterable<Event> findEventsByHoliday(String holiday);

	Iterable<Event> findEventsByConfession(String confession);

	Iterable<Event> findEventsByFoodPref(String foodPreference);

	//Event getEvent(String login, LocalDateTime dateCreated);

}
