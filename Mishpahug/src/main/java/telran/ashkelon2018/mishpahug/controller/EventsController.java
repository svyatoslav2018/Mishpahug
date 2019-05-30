package telran.ashkelon2018.mishpahug.controller;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/creation")
	public CodeResponseDto addEvent(@RequestBody AddEventDto newEvent, Principal principal) {
		return eventsService.addNewEvent(newEvent,principal.getName());
	}

	@GetMapping("/calendar/{month}")
	public EventListForCalendarDto calendar(@PathVariable int month,Principal principal) {
		return eventsService.eventListForCalendar(month, principal.getName());
	}
	
	@GetMapping("/currentlist")
	public MyEventsListRespDto getMyEventsList(Principal principal) {	
		return eventsService.myEventsList(principal.getName());
	}

	@PutMapping("/subscription/{eventId}")
	public CodeResponseDto subscribe(@PathVariable String eventId, Principal principal) {
		return eventsService.addSubscribe(eventId, principal.getName());
	}

	@PutMapping("/unsubscription/{eventId}")
	public CodeResponseDto unsubscribe(@PathVariable String eventId, Principal principal) {
		return eventsService.delSubscribe(eventId, principal.getName());
	}

	// without authentication
	@PostMapping("/allprogresslist")
	public EventListResponseDto findAllEventsInProgress(@RequestParam int page, @RequestParam int size,
			@RequestBody EventListRequestDto eventListRequestDto, Principal principal) {
		return eventsService.findEventsInProgress(eventListRequestDto, page, size, principal);
	}

}