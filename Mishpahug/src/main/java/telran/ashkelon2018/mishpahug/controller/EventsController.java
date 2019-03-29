package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.configuration.SessionConfiguration;
import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventInfoResponseDto;
import telran.ashkelon2018.mishpahug.service.EventsService;

@CrossOrigin
@RestController
@RequestMapping("/event") // all will be start from event

public class EventsController {

	@Autowired
	EventsService eventsService;

	@Autowired
	SessionConfiguration sessionConfiguration;

	@PostMapping("/creation")
	public CodeResponseDto addEvent(@RequestBody AddEventDto newEvent) {
		String sessionLogin = sessionConfiguration.sessionUserName();

		return eventsService.addNewEvent(newEvent, sessionLogin);
	}
	 @GetMapping("/calendar/{month}") // id=login+dateCreated
		 public Event getEvent(@PathVariable String login, @PathVariable LocalDateTime
		 dateCreated) {
		 return eventsService.getEvent(login, dateCreated);
		 }
	 
	 
	 @GetMapping("/own/{eventId}")
	 public MyEventInfoResponseDto getMyEventInfo(@PathVariable String eventId) {
	 return eventsService.getMyEventInfo(eventId);
	 }
	 
	// @GetMapping("/event/{eventId}") // id=login+dateCreated
	// public Event getEvent(@PathVariable String login, @PathVariable LocalDateTime
	// dateCreated) {
	// return siteService.getEvent(login, dateCreated);
	// }

	// i think delete must be automaticaly after getting status "done"+some days
	// @DeleteMapping("/event/{login,dateCreated}") // id=login,dateCreated
	// public Event removeEvent(@PathVariable String login, @PathVariable
	// LocalDateTime dateCreated,
	// @RequestBody String token) {
	// return service.removeEvent(login, dateCreated, token);
	// }

	// @PutMapping("/event/{login,dateCreated}/rate")
	// public boolean addRating(@PathVariable String login, @PathVariable
	// LocalDateTime dateCreated) {
	// return service.addRating(login, dateCreated);
	// }

	// @PostMapping("/events/city") // how get city from address
	// public Iterable<Event> getEventsByCity(@PathVariable String city) {
	// return service.findEventsByCity(city);
	// }

	// @PostMapping("/events/period")
	// public Iterable<Event> getEventsBetweenDates(@RequestBody DatePeriodDto
	// periodDto) {
	// return service.findEventsByDates(periodDto);
	// }

	// @PostMapping("/events/holiday")
	// public Iterable<Event> getEventsByHoliday(@PathVariable String holiday) {
	// return service.findEventsByHoliday(holiday);
	// }

	// @PostMapping("/events/confession")
	// public Iterable<Event> getEventsByConfession(@PathVariable String confession)
	// {
	// return service.findEventsByConfession(confession);
	// }

	// @PostMapping("/events/foodPref")
	// public Iterable<Event> getEventsByFoodPref(@PathVariable String
	// foodPreference) {
	// return service.findEventsByFoodPref(foodPreference);
	// }

	// without authentication
	@PostMapping("/allprogresslist") 
	public EventListResponseDto findAllEventsInProgress(@RequestParam int page, @RequestParam int size,
			@RequestBody EventListRequestDto eventListRequestDto) {
		System.out.println("page " + page + " size " + size);
		// String sessionLogin = sessionConfiguration.sessionUserName();
		return eventsService.findEventsInProgress(eventListRequestDto, page, size);

	}
}
