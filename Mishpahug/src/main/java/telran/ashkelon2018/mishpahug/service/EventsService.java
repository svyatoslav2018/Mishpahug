package telran.ashkelon2018.mishpahug.service;

import java.security.Principal;

import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListForCalendarDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsListRespDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsToResp;

public interface EventsService {

	CodeResponseDto addNewEvent(AddEventDto newEvent,String sessionLogin);

	EventListResponseDto findEventsInProgress(EventListRequestDto eventListRequestDto, int page, int size, Principal principal);																											

	CodeResponseDto addSubscribe(String eventId, String sessionLogin);

	CodeResponseDto delSubscribe(String eventId, String sessionLogin);

	EventListForCalendarDto eventListForCalendar(int month, String sessionLogin);

	MyEventsListRespDto myEventsList(String sessionLogin);

	MyEventsToResp myEventInfo(int eventId, String sessionLogin);

	

}
