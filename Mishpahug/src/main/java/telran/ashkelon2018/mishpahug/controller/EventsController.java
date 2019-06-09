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
import telran.ashkelon2018.mishpahug.dto.ChangeEventStatusDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListForCalendarDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.InvitationResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsListRespDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsToResp;
import telran.ashkelon2018.mishpahug.dto.MyHistoryListRespDto;
import telran.ashkelon2018.mishpahug.dto.ParticipationListRespDto;
import telran.ashkelon2018.mishpahug.dto.SubscribedEventToResp;
import telran.ashkelon2018.mishpahug.service.EventsService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping 
public class EventsController {

	@Autowired
	EventsService eventsService;

	@PostMapping("/event/creation")
	public CodeResponseDto addEvent(@RequestBody AddEventDto newEvent, Principal principal) {
		return eventsService.addNewEvent(newEvent, principal.getName());
	}

	@GetMapping("/event/calendar/{month}")
	public EventListForCalendarDto calendar(@PathVariable int month, Principal principal) {
		return eventsService.eventListForCalendar(month, principal.getName());
	}

	@GetMapping("/event/currentlist")
	public MyEventsListRespDto getMyEventsList(Principal principal) {
		return eventsService.myEventsList(principal.getName());
	}

	@PutMapping("/event/subscription/{eventId}")
	public CodeResponseDto subscribe(@PathVariable String eventId, Principal principal) {
		return eventsService.addSubscribe(eventId, principal.getName());
	}

	@PutMapping("/event/unsubscription/{eventId}")
	public CodeResponseDto unsubscribe(@PathVariable String eventId, Principal principal) {
		return eventsService.delSubscribe(eventId, principal.getName());
	}

	@GetMapping("/event/own/{eventId}")
	public MyEventsToResp getMyEventInfo(@PathVariable String eventId, Principal principal) {
		return eventsService.myEventInfo(eventId, principal.getName());
	}

	@GetMapping("/event/subscribed/{eventId}")
	public SubscribedEventToResp getSubscribedEventInfo(@PathVariable String eventId, Principal principal) {
		return eventsService.subscribedEventInfo(eventId, principal.getName());
	}

	@GetMapping("/event/participationlist")
	public ParticipationListRespDto getParticipationList(Principal principal) {
		return eventsService.participationList(principal.getName());
	}

	@PutMapping("/event/invitation/{eventId}/{subscriberId}")
	public InvitationResponseDto invitation(@PathVariable String eventId, @PathVariable String subscriberId) {
		return eventsService.invitationToEvent(eventId, subscriberId);
	}

	@PutMapping("/event/vote/{eventId}/{voteCount}")
	public CodeResponseDto vote(@PathVariable String eventId, @PathVariable Double voteCount, Principal principal) {
		return eventsService.voteForEvent(eventId, voteCount, principal.getName());
	}
	
	@PutMapping("/event/pending/{eventId}")
	public ChangeEventStatusDto changeStatus(@PathVariable String eventId, Principal principal) {
		return eventsService.changeEventStatusOnPending(eventId, principal.getName());
	}
	
	@GetMapping("/event/historylist")
	public MyHistoryListRespDto getРistoryList(Principal principal) {
		return eventsService.historyList(principal.getName());
		
	}

	// without authentication
	@PostMapping("/event/allprogresslist")
	public EventListResponseDto findAllEventsInProgress(@RequestParam int page, @RequestParam int size,
			@RequestBody EventListRequestDto eventListRequestDto, Principal principal) {
		return eventsService.findEventsInProgress(eventListRequestDto, page, size, principal);
	}

}
