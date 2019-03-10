package telran.ashkelon2018.mishpahug.service;

import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventDto;

public interface EventsService {

	CodeResponseDto addNewEvent(EventDto newEvent, String sessionLogin);

	Iterable<Event> findEventsInProgressUnAuth(String status, EventDto eventDto, Integer page, Integer size,
			String sessionLogin);

	//Event getEvent(String login, LocalDate date, LocalTime time);// id = login+date+time

//	Event removeEvent(String status, String login, LocalDate date, LocalTime time);// status "done" or if owner
//																					// canceled
//
//	boolean addRating(String login, LocalDate date, LocalTime time);// id = login+date+time
//
//	Iterable<Event> findAllEvents(String status);// status "In progress"
//
//	Iterable<Event> findEventsByCity(String city);
//
//	Iterable<Event> findEventsByDates(LocalDate dateFrom,LocalDate dateTo);
//
//	Iterable<Event> findEventsByHoliday(String holiday);
//
//	Iterable<Event> findEventsByConfession(String confession);
//
//	Iterable<Event> findEventsByFoodPref(String foodPreference);

	//Event getEvent(String login, LocalDateTime dateCreated);

}
