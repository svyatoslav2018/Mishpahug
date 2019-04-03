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
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.configuration.EventConfiguration;
import telran.ashkelon2018.mishpahug.configuration.SessionConfiguration;
import telran.ashkelon2018.mishpahug.dao.EventSubscribeRepository;
import telran.ashkelon2018.mishpahug.dao.EventsRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.EventOwner;
import telran.ashkelon2018.mishpahug.domain.EventSubscribe;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.exceptions.UserConflictException;
import telran.ashkelon2018.mishpahug.exceptions.UserNotFoundException;
import telran.ashkelon2018.mishpahug.exceptions.WrongLoginOrPasswordException;

@Builder
@Service
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventsRepository eventsRepository;

	@Autowired
	EventSubscribeRepository eventSubscribeRepository;

	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Autowired
	EventConfiguration eventConfiguration;
	
	@Autowired
	RunThroughFiltersMT runThroughFilters;

	@Override
	public CodeResponseDto addNewEvent(AddEventDto newEvent,
			String sessionLogin) {

		UserAccount userAccount = userRepository.findById(sessionLogin).get();
		if (!sessionLogin.equals(userAccount.getLogin())) {
			throw new WrongLoginOrPasswordException();// 401 unauthorized
		}
		LocalDateTime dateFrom = newEvent.getDate().atTime(newEvent.getTime());// LocalDateTime.of(newEvent.getDate(),
																				// newEvent.getTime());//
		LocalDateTime dateTo = dateFrom.plusHours(newEvent.getDuration());
		LocalDateTime checkDateFrom = LocalDateTime.of(newEvent.getDate(),
				newEvent.getTime());
		LocalDateTime checkDateTo = checkDateFrom
				.plusMinutes(newEvent.getDuration());
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
			// throw new UnprocessableEntityException();// 422 Invalid data
			return new CodeResponseDto(422, "Invalid data");
		}

		String eventId = userAccount.getLogin() + "D"
				+ newEvent.getDate().toString().replaceAll("\\-", "") + "T"
				+ newEvent.getTime().toString();
		if (eventsRepository.findById(eventId).orElse(null) != null) {
			throw new UserConflictException();// 409 busy date
		}

		// LocalDateTime localDateTimeEvent =
		// LocalDateTime.of(newEvent.getDate(),
		// newEvent.getTime());
		Event event = Event.builder().eventId(eventId).owner(sessionLogin)
				.title(newEvent.getTitle()).holiday(newEvent.getHoliday())
				.address(newEvent.getAddress())
				.confession(newEvent.getConfession())
				// .localDateTimeEvent(localDateTimeEvent)
				.date(newEvent.getDate()).time(newEvent.getTime())
				.duration(newEvent.getDuration()).food(newEvent.getFood())
				.description(newEvent.getDescription())
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

		// System.out.println("!!!!! listOfEvents " + listOfEvents);
		long totalElements = listOfEvents.getTotalElements();
		// System.out.println("!!!!! totalElements " + totalElements);
		int totalPages = listOfEvents.getTotalPages();
		// System.out.println("!!!!! totalPages " + totalPages);
		int number = listOfEvents.getNumber();
		// System.out.println("!!!!! number " + number);
		int numberOfElements = listOfEvents.getNumberOfElements();
		// System.out.println("!!!!! numberOfElements " + numberOfElements);
		boolean first = listOfEvents.isFirst();
		// System.out.println("!!!!! first " + first);
		boolean last = listOfEvents.isLast();
		// System.out.println("!!!!! last " + last);
		Sort sort = listOfEvents.getSort();
		// System.out.println("!!!!! sort " + sort);

		List<FullEvent2Resp> content = new ArrayList<>();
		listOfEvents.forEach(e -> content.add(eventToEventDtoConverter(e)));
		// for (FullEvent2Resp i : content) {
		// System.out.println(i);
		// }

		Stream<FullEvent2Resp> stream = content.stream();
		return new EventListResponseDto(stream.collect(Collectors.toList()),
				totalElements, totalPages, size, number, numberOfElements,
				first, last, sort);

	}

	private FullEvent2Resp eventToEventDtoConverter(Event e) {
		UserAccount ownerInfo = userRepository.findById(e.getOwner()).get();
		String fullName = ownerInfo.getFirstName() + " "
				+ ownerInfo.getLastName();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate birthdate = LocalDate.parse(ownerInfo.getDateOfBirth(),
				formatter);
		Integer age = (int) ChronoUnit.YEARS.between(birthdate,
				LocalDate.now());
		return FullEvent2Resp.builder().eventId(e.getEventId())
				.title(e.getTitle()).holiday(e.getHoliday())
				.confession(e.getConfession()).date(e.getDate())
				.time(e.getTime()).duration(e.getDuration())
				.address(e.getAddress()).food(e.getFood())
				.description(e.getDescription())
				.owner(EventOwner.builder().fullName(fullName)
						.confession(ownerInfo.getConfession())
						.gender(ownerInfo.getGender()).age(age)
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
				.orElseThrow(UserNotFoundException::new);// .get()
		String candidatPassword = credentials.getPassword();
		if (!credentials.getLogin().equals(userAccount.getLogin()) || !BCrypt
				.checkpw(candidatPassword, userAccount.getPassword())) {
			// throw new WrongLoginOrPasswordException();// 401 unauthorized
			return new CodeResponseDto(401, "User unauthorized!");
		}
		try {
			boolean isInvited = false;
			EventSubscribe es = new EventSubscribe(eventId,
					credentials.getLogin(), isInvited);
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
			// throw new WrongLoginOrPasswordException();// 401 unauthorized
			return new CodeResponseDto(401, "User unauthorized!");
		}
		try {
			EventSubscribe es = new EventSubscribe(eventId,
					credentials.getLogin());
			eventSubscribeRepository.delete(es);
			return new CodeResponseDto(200, "User unsubscribed from the event!");
		} catch (Exception e) {
			return new CodeResponseDto(409,
					"User can't unsubscribe from the event!");
		}

	}

}
