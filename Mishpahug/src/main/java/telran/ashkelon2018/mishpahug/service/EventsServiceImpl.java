package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.configuration.EventConfiguration;
import telran.ashkelon2018.mishpahug.dao.EventSubscribeRepository;
import telran.ashkelon2018.mishpahug.dao.EventsRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
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
import telran.ashkelon2018.mishpahug.dto.MyEventInfoResponseDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsListRespDto;
import telran.ashkelon2018.mishpahug.dto.MyEventsToResp;
import telran.ashkelon2018.mishpahug.exceptions.UserNotFoundException;
import telran.ashkelon2018.mishpahug.exceptions.WrongLoginOrPasswordException;

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
		if (!sessionLogin.equals(userAccount.getLogin())) {
			throw new WrongLoginOrPasswordException(401, "unauthorized");
		}
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
		System.out.println(newEvent.getOwner());
		System.out.println(event.getOwner());
		return new CodeResponseDto(200, "Event is created");
	}

	@Override
	public EventListResponseDto findEventsInProgress(EventListRequestDto body,
			int page, int size) {
		Pageable pageable = PageRequest.of(page, size,
				new Sort(Sort.Direction.ASC, "date"));
		Page<Event> listOfEvents = runThroughFilters.madeListWithFilter(body,
				pageable);

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

	@Override
	public CodeResponseDto addSubscribe(String eventId, String token) {
		AccountUserCredentials credentials = accountConfiguration
				.tokenDecode(token);
		UserAccount userAccount = userRepository
				.findById(credentials.getLogin())
				.orElseThrow(UserNotFoundException::new);
		String candidatPassword = credentials.getPassword();

		if (!credentials.getLogin().equals(userAccount.getLogin()) || !BCrypt
				.checkpw(candidatPassword, userAccount.getPassword())) {
			return new CodeResponseDto(401, "User unauthorized!");
		}
		try {
			EventSubscribe es = new EventSubscribe(eventId,
					credentials.getLogin(), false);
			eventSubscribeRepository.save(es);
			return new CodeResponseDto(200, "User subscribed to the event!");
		} catch (Exception e) {
			return new CodeResponseDto(409,
					"User is the owner of the event or already subscribed to it!");
		}

	}

	@Override
	public CodeResponseDto delSubscribe(String eventId, String token) {
		AccountUserCredentials credentials = accountConfiguration
				.tokenDecode(token);
		UserAccount userAccount = userRepository
				.findById(credentials.getLogin())
				.orElseThrow(UserNotFoundException::new);// .get()
		String candidatPassword = credentials.getPassword();

		if (!credentials.getLogin().equals(userAccount.getLogin()) || !BCrypt
				.checkpw(candidatPassword, userAccount.getPassword())) {
			return new CodeResponseDto(401, "User unauthorized!");
		}
		try {
			EventSubscribe es = new EventSubscribe(eventId,
					credentials.getLogin());
			eventSubscribeRepository.delete(es);
			return new CodeResponseDto(200,
					"User unsubscribed from the event!");
		} catch (Exception e) {
			return new CodeResponseDto(409,
					"User can't unsubscribe from the event!");
		}
	}

	@Override
	public MyEventsListRespDto myEventsList(String token) {
		AccountUserCredentials credentials = accountConfiguration
				.tokenDecode(token);
		UserAccount userAccount = userRepository
				.findById(credentials.getLogin())
				.orElseThrow(UserNotFoundException::new);
		// String candidatPassword = credentials.getPassword();
		if (!credentials.getLogin().equals(userAccount.getLogin()))
		// || !BCrypt.checkpw(candidatPassword, userAccount.getPassword()))
		{
			throw new WrongLoginOrPasswordException(401, "unauthorized");// 401,
																			// "unauthorized"
		}

		String owner = credentials.getLogin();
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
			System.out.println("pageable = " + pageable);
			listOfEvents = eventsRepository.findByOwnerAndEventStatus(owner, s,
					pageable);
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

	private List<SubscriberInfo> participantsToParticipantsDtoConverter(
			Event p) {
		List<EventSubscribe> subscribersInfo = eventSubscribeRepository
				.findByEventId(p.getEventId());
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
	public EventListForCalendarDto eventListForCalendar(int month,
			String token) {
		// AccountUserCredentials credentials =
		// accountConfiguration.tokenDecode(token);
		// UserAccount userAccount =
		// userRepository.findById(credentials.getLogin())
		// .orElseThrow(UserNotFoundException::new);
		// String candidatPassword = credentials.getPassword();
		// if (!credentials.getLogin().equals(userAccount.getLogin())
		// || !BCrypt.checkpw(candidatPassword, userAccount.getPassword())) {
		// throw new WrongLoginOrPasswordException(401, "unauthorized");
		// }
		//
		// List<E>
		// List<FullEventToResp> myEvents = new ArrayList<>();
		// List<FullEventToResp> subscribedEvents = new ArrayList<>();
		//
		// String owner = credentials.getLogin();
		// String[] statuses =
		// {EventConfiguration.INPROGRESS,EventConfiguration.PENDING};
		//
		//
		// listOfEvents = eventsRepository.findByOwnerAndEventStatus(owner,
		// EventConfiguration.INPROGRESS,EventConfiguration.PENDING);
		// listOfEvents.forEach(e ->
		// myEvents.add(myEventsToCalendarDtoConverter(e)));
		// }
		//
		return null;
	}

	@Override
	public MyEventInfoResponseDto myEventInfo(String eventId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
