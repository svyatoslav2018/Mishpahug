package telran.ashkelon2018.mishpahug.service;

import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CalendarDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsListRespDto;

public interface EventsService {

	CodeResponseDto addNewEvent(AddEventDto newEvent,String sessionLogin);

	EventListResponseDto findEventsInProgress(EventListRequestDto eventListRequestDto, int page, int size);																											

	CodeResponseDto addSubscribe(String eventId, String sessionLogin);

	CodeResponseDto delSubscribe(String eventId, String sessionLogin);

	//MyEventInfoResponseDto myEventInfo(String eventId, String sessionLogin);


	CalendarDto eventListForCalendar(int month, String sessionLogin);

	MyEventsListRespDto myEventsList(String sessionLogin);

	

}
