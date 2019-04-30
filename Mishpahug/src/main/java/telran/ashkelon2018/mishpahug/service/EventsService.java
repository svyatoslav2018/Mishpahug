package telran.ashkelon2018.mishpahug.service;

import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListForCalendarDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventInfoResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsListRespDto;

public interface EventsService {

	CodeResponseDto addNewEvent(AddEventDto newEvent, String sessionLogin);

	EventListResponseDto findEventsInProgress(EventListRequestDto eventListRequestDto, int page, int size);																											

	CodeResponseDto addSubscribe(String eventId, String token);

	CodeResponseDto delSubscribe(String eventId, String token);

	MyEventInfoResponseDto myEventInfo(String eventId, String token);

	EventListForCalendarDto eventListForCalendar(String month, String token);

	MyEventsListRespDto myEventsList(String token);

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
