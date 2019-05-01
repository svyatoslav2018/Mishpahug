package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.configuration.SessionConfiguration;
import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListForCalendarDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsListRespDto;
import telran.ashkelon2018.mishpahug.service.EventsService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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

	@GetMapping("/calendar/{month}")
	public EventListForCalendarDto calendar(@PathVariable int month, @RequestHeader("Authorization") String token) {
		return eventsService.eventListForCalendar(month, token);
	}

//	@GetMapping("/own/{eventId}")
//	public MyEventInfoResponseDto getMyEventInfo(@PathVariable String eventId, @RequestHeader("Authorization") String token) {
//		return eventsService.myEventInfo(eventId, token);
//	}
	
	@GetMapping("/currentlist")
	public MyEventsListRespDto getMyEventsList(@RequestHeader("Authorization") String token) {
		return eventsService.myEventsList(token);
	}
	
	

	@PutMapping("/subscription/{eventId}")
	public CodeResponseDto subscribe(@PathVariable String eventId, @RequestHeader("Authorization") String token) {
		System.out.println("eventId " + eventId + " " + "token " + token);
		return eventsService.addSubscribe(eventId, token);
	}

	@PutMapping("/unsubscription/{eventId}")
	public CodeResponseDto unsubscribe(@PathVariable String eventId, @RequestHeader("Authorization") String token) {
		System.out.println("eventId " + eventId + " " + "token " + token);
		return eventsService.delSubscribe(eventId, token);
	}

	// without authentication
	@PostMapping("/allprogresslist")
	public EventListResponseDto findAllEventsInProgress(@RequestParam int page, @RequestParam int size,
			@RequestBody EventListRequestDto eventListRequestDto) {
		return eventsService.findEventsInProgress(eventListRequestDto, page, size);
	}

}
