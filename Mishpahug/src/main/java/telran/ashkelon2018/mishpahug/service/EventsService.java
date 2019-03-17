package telran.ashkelon2018.mishpahug.service;

import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;

public interface EventsService {

	CodeResponseDto addNewEvent(AddEventDto newEvent, String sessionLogin);
	// Iterable<Event> findEventsInProgress(EventListRequestDto
	// eventsListFilterDto,String sessionLogin);

	EventListResponseDto findEventsInProgress( Integer page, Integer size, EventListRequestDto eventListRequestDto);

	// Event getEvent(String login, LocalDate date, LocalTime time);// id =
	// login+date+time

	// Event removeEvent(String status, String login, LocalDate date, LocalTime
	// time);// status "done" or if owner
	// // canceled
	//
	// boolean addRating(String login, LocalDate date, LocalTime time);// id =
	// login+date+time
	//
	// Iterable<Event> findAllEvents(String status);// status "In progress"
	//
	// Iterable<Event> findEventsByCity(String city);
	//
	// Iterable<Event> findEventsByDates(LocalDate dateFrom,LocalDate dateTo);
	//
	// Iterable<Event> findEventsByHoliday(String holiday);
	//
	// Iterable<Event> findEventsByConfession(String confession);
	//
	// Iterable<Event> findEventsByFoodPref(String foodPreference);

	// Event getEvent(String login, LocalDateTime dateCreated);

}
