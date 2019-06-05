package telran.ashkelon2018.mishpahug.service;


import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.EventConfiguration;
import telran.ashkelon2018.mishpahug.dao.EventSubscribeRepository;
import telran.ashkelon2018.mishpahug.dao.EventsRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.EventCalendar;
import telran.ashkelon2018.mishpahug.domain.EventOwner;
import telran.ashkelon2018.mishpahug.domain.EventSubscribe;
import telran.ashkelon2018.mishpahug.domain.SubscriberInfo;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListForCalendarDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.FullEventToResp;
import telran.ashkelon2018.mishpahug.dto.InvitationResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsListRespDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsToResp;
import telran.ashkelon2018.mishpahug.dto.ParticipationListRespDto;
import telran.ashkelon2018.mishpahug.dto.ParticipationListToResp;
import telran.ashkelon2018.mishpahug.dto.SubscribedEventToResp;
import telran.ashkelon2018.mishpahug.exceptions.UserConflictException;

@Builder
@Service
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventsRepository eventsRepository;

	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Autowired
	EventConfiguration eventConfiguration;

	@Autowired
	RunThroughFiltersMT runThroughFilters;

	@Autowired

	EventSubscribeRepository eventSubscribeRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public CodeResponseDto addNewEvent(AddEventDto newEvent,
			String sessionLogin) {

		UserAccount userAccount = userRepository.findById(sessionLogin).get();		
		LocalDateTime dateFrom = newEvent.getDate().atTime(newEvent.getTime());
		LocalDateTime dateTo = dateFrom.plusHours(newEvent.getDuration());

		boolean checktime1 = LocalDateTime.now()
				.isBefore(dateFrom.minusHours(48));
		boolean checktime2 = LocalDateTime.now()
				.isAfter(dateFrom.minusMonths(2));
		boolean checktime3 = false;

		List<Event> list = eventsRepository
				.findByDurationAndOwnerAndDateAndTime(newEvent.getDuration(),
						newEvent.getOwner(), newEvent.getDate(),
						newEvent.getTime());
		if (list.isEmpty()) {
			checktime3 = true;
		}
		if (!list.isEmpty()) {
			checktime3 = true;
			for (Event event : list) {
				LocalDateTime dateTimeFromDateAndTime = LocalDateTime
						.of(event.getDate(), event.getTime());
				if (!dateTimeFromDateAndTime.isBefore(dateFrom)
						&& !dateTo.isBefore(dateTimeFromDateAndTime)) {
					checktime3 = false;
				}
			}
		}

		if (!(checktime1 && checktime2 && checktime3)) {
			return new CodeResponseDto(422, "Invalid data");
		}

		String eventId = userAccount.getLogin() + "D"
				+ newEvent.getDate().toString().replaceAll("\\-", "") + "T"
				+ newEvent.getTime().toString();

		if (eventsRepository.findById(eventId).orElse(null) != null) {
			return new CodeResponseDto(409,
					"This user has already created the event on this date and time!");

		}

		Event event = Event.builder().eventId(eventId).owner(sessionLogin)
				.title(newEvent.getTitle()).holiday(newEvent.getHoliday())
				.address(newEvent.getAddress())
				.confession(newEvent.getConfession()).date(newEvent.getDate())
				.time(newEvent.getTime()).duration(newEvent.getDuration())
				.food(newEvent.getFood()).description(newEvent.getDescription())
				.eventStatus(newEvent.getEventStatus()).build();

		event.setEventStatus(EventConfiguration.INPROGRESS);
		eventsRepository.save(event);
		return new CodeResponseDto(200, "Event is created");
	}

	@Override
	public EventListResponseDto findEventsInProgress(EventListRequestDto body,
			int page, int size, Principal principal) {
		System.out.println("findEventsInProgress " + principal);
		Pageable pageable = PageRequest.of(page, size,
				new Sort(Sort.Direction.ASC, "date"));
		Page<Event> listOfEvents = runThroughFilters.madeListWithFilter(body,
				pageable, principal);

		long totalElements = listOfEvents.getTotalElements();
		int totalPages = listOfEvents.getTotalPages();
		int number = listOfEvents.getNumber();
		int numberOfElements = listOfEvents.getNumberOfElements();
		boolean first = listOfEvents.isFirst();
		boolean last = listOfEvents.isLast();
		Sort sort = listOfEvents.getSort();

		List<FullEventToResp> content = new ArrayList<>();
		listOfEvents.forEach(e -> content.add(eventToEventDtoConverter(e)));
		Stream<FullEventToResp> stream = content.stream();

		return new EventListResponseDto(stream.collect(Collectors.toList()),
				totalElements, totalPages, size, number, numberOfElements,
				first, last, sort);
	}

	private FullEventToResp eventToEventDtoConverter(Event e) {

		UserAccount ownerInfo = userRepository.findById(e.getOwner()).get();

		String fullName = ownerInfo.getFirstName() + " "
				+ ownerInfo.getLastName();
		return FullEventToResp.builder().eventId(e.getEventId())
				.title(e.getTitle()).holiday(e.getHoliday())
				.confession(e.getConfession()).date(e.getDate())
				.time(e.getTime()).duration(e.getDuration())
				.address(e.getAddress()).food(e.getFood())
				.description(e.getDescription())
				.owner(EventOwner.builder().fullName(fullName)
						.confession(ownerInfo.getConfession())
						.gender(ownerInfo.getGender()).age(calcAge(ownerInfo))
						.pictureLink(ownerInfo.getPictureLink())
						.maritalStatus(ownerInfo.getMaritalStatus())
						.foodPreferences(ownerInfo.getFoodPreferences())
						.languages(ownerInfo.getLanguages())
						.rate(ownerInfo.getRate()).build())
				.build();
	}

	/*I think we not realized that condition!
	 * User can subscribe to multiple events on the same date with the only
	 * condition: if he will be confirmed/invited to one the subscribed events on
	 * this date, the other subscriptions will be cancelled.
	 */
	@Override
	public CodeResponseDto addSubscribe(String eventId, String sessionLogin) {
		
		try {
			EventSubscribe es = new EventSubscribe(eventId, sessionLogin, false);
			eventSubscribeRepository.save(es);
			return new CodeResponseDto(200, "User subscribed to the event!");
		} catch (Exception e) {
			return new CodeResponseDto(409,
					"User is the owner of the event or already subscribed to it!");
		}
	}

	@Override
	public CodeResponseDto delSubscribe(String eventId, String sessionLogin) {
		try {
			EventSubscribe es = new EventSubscribe(eventId, sessionLogin);
			eventSubscribeRepository.delete(es);
			return new CodeResponseDto(200,
					"User unsubscribed from the event!");
		} catch (Exception e) {
			return new CodeResponseDto(409,
					"User can't unsubscribe from the event!");
		}
	}

	@Override
	public MyEventsListRespDto myEventsList(String sessionLogin) {

		Pageable pageable;
		List<MyEventsToResp> events = new ArrayList<>();
		Page<Event> listOfEvents;
		String[] statuses = {EventConfiguration.INPROGRESS,
				EventConfiguration.PENDING, EventConfiguration.DONE};

		for (String s : statuses) {
			if (s == EventConfiguration.DONE) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE,
						new Sort(Sort.Direction.DESC, "date"));
			} else {
				pageable = PageRequest.of(0, Integer.MAX_VALUE,
						new Sort(Sort.Direction.ASC, "date"));
			}
			listOfEvents = eventsRepository
					.findByOwnerAndEventStatus(sessionLogin, s, pageable);
			listOfEvents
					.forEach(e -> events.add(myEventsToEventDtoConverter(e)));
		}
		
		Stream<MyEventsToResp> stream = events.stream();
		return new MyEventsListRespDto(stream.collect(Collectors.toList()));
	}

	private MyEventsToResp myEventsToEventDtoConverter(Event e) {
		return MyEventsToResp.builder().eventId(e.getEventId())
				.title(e.getTitle()).holiday(e.getHoliday())
				.confession(e.getConfession()).date(e.getDate())
				.time(e.getTime()).duration(e.getDuration()).food(e.getFood())
				.description(e.getDescription()).eventStatus(e.getEventStatus())
				.participants(participantsToParticipantsDtoConverter(e))
				.build();
	}

	private List<SubscriberInfo> participantsToParticipantsDtoConverter(Event p) {
		List<EventSubscribe> subscribersInfo = eventSubscribeRepository.findByEventId(p.getEventId());
		UserAccount usersInfo;
		List<SubscriberInfo> subInfo = new ArrayList<>();
		for (int i = 0; i < subscribersInfo.size(); i++) {
			usersInfo = userRepository
					.findById(subscribersInfo.get(i).getSubscriberId())
					.orElse(null);
			SubscriberInfo element = SubscriberInfo.builder()
					.userId(subscribersInfo.get(i).getSubscriberId())
					.fullName(usersInfo.getFirstName() + " "
							+ usersInfo.getLastName())
					.confession(usersInfo.getConfession())
					.gender(usersInfo.getGender()).age(calcAge(usersInfo))
					.pictureLink(usersInfo.getPictureLink())
					.maritalStatus(usersInfo.getMaritalStatus())
					.foodPreferences(usersInfo.getFoodPreferences())
					.languages(usersInfo.getLanguages())
					.rate(usersInfo.getRate())
					.numberOfVoters(usersInfo.getNumberOfVoters())
					.isInvited(subscribersInfo.get(i).getIsInvited()).build();
			subInfo.add(element);
		}
		return subInfo;
	}

	private Integer calcAge(UserAccount usersInfo) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate birthdate = LocalDate.parse(usersInfo.getDateOfBirth(),
				formatter);
		return (int) ChronoUnit.YEARS.between(birthdate, LocalDate.now());
	}

	@Override
	public EventListForCalendarDto eventListForCalendar(int month,String sessionLogin) {
		
		List<Event> myEventsFromRep = eventsRepository.findByOwner(sessionLogin);
		myEventsFromRep=filteredStatus(myEventsFromRep);
		List<EventCalendar> myEventsCal = eventsToClndr(myEventsFromRep);

		List<EventSubscribe> listSubscrEventId = eventSubscribeRepository.findBySubscriberId(sessionLogin);
		List<Event> sbscrbEventsFromRep = new ArrayList<>();
		for (int i = 0; i < listSubscrEventId.size(); i++) {
			String eventId = listSubscrEventId.get(i).getEventId();
			sbscrbEventsFromRep.addAll(eventsRepository.findByEventId(eventId));
		}
		sbscrbEventsFromRep=filteredStatus(sbscrbEventsFromRep);
		List<EventCalendar> subscribedEvents = eventsToClndr(sbscrbEventsFromRep);

		return EventListForCalendarDto.builder()
				.myEvents(myEventsCal)
				.subscribedEvents(subscribedEvents)
				.build();
	}

	private List<Event> filteredStatus(List<Event> fullList) {
		return 	fullList.stream().filter(ev->(ev.getEventStatus()
				.equals(EventConfiguration.INPROGRESS) ||
				ev.getEventStatus().equals( EventConfiguration.PENDING)))
				//ev.getEventStatus().equals( EventConfiguration.PENDING))&&ev.getDate().getMonthValue()==month)
				.collect(Collectors.toList());
	}

	private List<EventCalendar> eventsToClndr(List<Event> eventsFromRep) {
		List<EventCalendar> eventsCalendar = new ArrayList<>();
		eventsFromRep.forEach(e->eventsCalendar.add(EventCalendar.builder()
				.eventId(e.getEventId())
				.title(e.getTitle())
				.date(e.getDate())
				.time(e.getTime())
				.duration(e.getDuration())
				.eventStatus(e.getEventStatus())
				.build()));
		return eventsCalendar;
	}

	@Override
	public MyEventsToResp myEventInfo(String eventId, String sessionLogin) {

		Event myEvent = eventsRepository.findByOwnerAndEventId(sessionLogin, eventId);

		if(myEvent==null) {
			throw new UserConflictException(409, "User is not associated with the event!");
		}
		return MyEventsToResp.builder()
				.eventId(myEvent.getEventId())
				.title(myEvent.getTitle())
				.holiday(myEvent.getHoliday())
				.confession(myEvent.getConfession())
				.date(myEvent.getDate())
				.time(myEvent.getTime())
				.duration(myEvent.getDuration())
				.food(myEvent.getFood())
				.description(myEvent.getDescription())
				.eventStatus(myEvent.getEventStatus())
				.participants(participantsToParticipantsDtoConverter(myEvent)).build();
	}

	@Override
	public SubscribedEventToResp subscribedEventInfo(String eventId, String sessionLogin) {
		Event subscribedEvent;
		
		EventSubscribe	subscribedEventId = eventSubscribeRepository.findBySubscriberIdAndEventId(sessionLogin, eventId);
			
		if (subscribedEventId == null) {
			throw new UserConflictException(409, "User is not associated with the event!");
		}
		
			subscribedEvent = eventsRepository.findByEventId(subscribedEventId.getEventId(),sessionLogin );
			UserAccount ownerInfo = userRepository.findById(subscribedEvent.getOwner()).get();
			String fullName = ownerInfo.getFirstName() + " " + ownerInfo.getLastName();		 			
			
			return SubscribedEventToResp.builder()
					.eventId(subscribedEvent.getEventId())
					.title(subscribedEvent.getTitle())
					.holiday(subscribedEvent.getHoliday())
					.confession(subscribedEvent.getConfession())
					.date(subscribedEvent.getDate())
					.time(subscribedEvent.getTime())
					.duration(subscribedEvent.getDuration())
					.address(subscribedEvent.getAddress())
					.food(subscribedEvent.getFood())
					.description(subscribedEvent.getDescription())
					.eventStatus(subscribedEvent.getEventStatus())
					.owner(EventOwner.builder()
							.fullName(fullName)
							.confession(ownerInfo.getConfession())
							.gender(ownerInfo.getGender()).age(calcAge(ownerInfo))
							.pictureLink(ownerInfo.getPictureLink())
							.maritalStatus(ownerInfo.getMaritalStatus())
							.foodPreferences(ownerInfo.getFoodPreferences())
							.languages(ownerInfo.getLanguages())
							.rate(ownerInfo.getRate())
							.numberOfVoters(ownerInfo.getNumberOfVoters())
							.build())
					.build();
	}

	//Sort by date does not work yet
	@Override
	public ParticipationListRespDto participationList(String sessionLogin) {
		
		List<ParticipationListToResp> events = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "date"));
		List<Event> listOfEvents= new ArrayList<>();
		List<String> listId = new ArrayList<>();

		List<EventSubscribe> listSubscrEventId = eventSubscribeRepository.findBySubscriberId(sessionLogin);
		for (int i = 0; i < listSubscrEventId.size(); i++) {
			String eventId = listSubscrEventId.get(i).getEventId();
			listId.add(eventId);			
		}
		System.out.println("listId!!!!!!!!"+listId);

		listId.forEach(i -> listOfEvents.addAll(eventsRepository.findByEventId(i, pageable)));

		listOfEvents.forEach(e -> events.add(participationListBuilder(e)));
						
		Stream<ParticipationListToResp> stream = events.stream();
		return new ParticipationListRespDto(stream.collect(Collectors.toList()));
	}
		
	private ParticipationListToResp participationListBuilder(Event e) {
		
		UserAccount ownerInfo = userRepository.findById(e.getOwner()).get();
		String fullName = ownerInfo.getFirstName() + " " + ownerInfo.getLastName();	
		
		return ParticipationListToResp.builder()
				.eventId(e.getEventId())
				.title(e.getTitle())
				.holiday(e.getHoliday())
				.confession(e.getConfession())
				.date(e.getDate())
				.time(e.getTime())
				.duration(e.getDuration())
				.address(e.getAddress())
				.food(e.getFood())
				.description(e.getDescription())
				.eventStatus(e.getEventStatus())
				.owner(EventOwner.builder()
						.fullName(fullName)
						.confession(ownerInfo.getConfession())
						.gender(ownerInfo.getGender()).age(calcAge(ownerInfo))
						.pictureLink(ownerInfo.getPictureLink())
						.maritalStatus(ownerInfo.getMaritalStatus())
						.foodPreferences(ownerInfo.getFoodPreferences())
						.languages(ownerInfo.getLanguages())
						.rate(ownerInfo.getRate())
						.numberOfVoters(ownerInfo.getNumberOfVoters())
						.build())
				.build();
	}

	@Override
	public InvitationResponseDto invitationToEvent(String eventId, String subscriberId) {

		Boolean isInvited = false;
		EventSubscribe es = eventSubscribeRepository.findBySubscriberIdAndEventIdAndIsInvited(subscriberId, eventId,
				isInvited);

		if (es == null) {
			throw new UserConflictException(409,
					"User is already invited to the event or is not subscribed to the event!");
		}
		es.setIsInvited(true);
		eventSubscribeRepository.save(es);
		EventSubscribe ess = eventSubscribeRepository.findBySubscriberIdAndEventId(subscriberId, eventId);
		return InvitationResponseDto.builder()
				.subscriberId(ess.getSubscriberId())
				.isInvited(ess.getIsInvited())
				.build();
	}
	
	
}
