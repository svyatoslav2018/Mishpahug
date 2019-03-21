package telran.ashkelon2018.mishpahug.service;

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
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.EventConfiguration;
import telran.ashkelon2018.mishpahug.dao.EventsRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.Filters;
import telran.ashkelon2018.mishpahug.domain.EventOwner;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.dto.FullEvent2Resp;
import telran.ashkelon2018.mishpahug.exceptions.UserConflictException;
import telran.ashkelon2018.mishpahug.exceptions.WrongLoginOrPasswordException;

@Builder
@Service
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventsRepository eventsRepository;

	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	EventConfiguration eventConfiguration;

	@Autowired
	RunThroughFilters runThroughFilters;

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

	// @Override
	// public EventListResponseDto findEventsInProgress(EventListRequestDto
	// eventsListFilterDto, String sessionLogin) {
	// // Integer page, Integer size,
	// // Event event=new Event();
	// // if (!(event.getEventStatus().equals(eventConfiguration.INPROGRESS))) {
	// // return null;
	// // }
	// String status = eventConfiguration.INPROGRESS;
	//// System.out.println(eventsListFilterDto);
	//// System.out.println(eventsRepository.findByEventStatus(status));
	// return eventsRepository.findByEventStatus(status);
	// }

	@Override
	public EventListResponseDto findEventsInProgress(EventListRequestDto body) {
		System.out.println("FILTER.body:: " + body);
		// Integer page, Integer size,
		// Point point = new
		// Point(body.getLocation().getLat(),body.getLocation().getLng());
		// Distance distance = new Distance(body.getLocation().getRadius());
		Filters filters = body.getFilters();
		Integer page = 0;
		Integer size = 100;
		Pageable pageable = PageRequest.of(page, size,
				new Sort(Sort.Direction.DESC, "dateFrom"));
		Page<Event> listOfEvents = runThroughFilters.madeListWithFilter(filters,
				pageable);

		System.out.println("!!!!! listOfEvents " + listOfEvents);
		long totalElements = listOfEvents.getTotalElements();
		System.out.println("!!!!! totalElements " + totalElements);
		int totalPages = listOfEvents.getTotalPages();
		System.out.println("!!!!! totalPages " + totalPages);
		int number = listOfEvents.getNumber();
		System.out.println("!!!!! number " + number);
		int numberOfElements = listOfEvents.getNumberOfElements();
		System.out.println("!!!!! numberOfElements " + numberOfElements);
		boolean first = listOfEvents.isFirst();
		System.out.println("!!!!! first " + first);
		boolean last = listOfEvents.isLast();
		System.out.println("!!!!! last " + last);
		Sort sort = listOfEvents.getSort();
		System.out.println("!!!!! sort " + sort);

		List<FullEvent2Resp> content = new ArrayList<>();
		listOfEvents.forEach(e -> content.add(eventToEventDtoConverter(e)));
		for (FullEvent2Resp i : content) {
			System.out.println(i);
		}

		Stream<FullEvent2Resp> stream = content.stream();

		boolean standartFilter = runThroughFilters.standartFilter;
		System.out
				.println("EventsServiceImpl.standartFilter " + standartFilter);
		if (!standartFilter) {
			if (filters.getDateFrom() != null) {
				System.out.println(filters.getDateFrom());
				stream = stream.filter(
						e -> e.getDate().isAfter(filters.getDateFrom()));
			}
			if (filters.getDateTo() != null) {
				System.out.println(filters.getDateTo());
				stream = stream
						.filter(e -> e.getDate().isBefore(filters.getDateTo()));
			}
			if (filters.getHolidays() != null) {
				System.out.println(filters.getHolidays());
				stream = stream.filter(e -> e.getHoliday()
						.equalsIgnoreCase(filters.getHolidays()));
			}
			if (filters.getConfession() != null) {
				System.out.println(filters.getConfession());
				stream = stream.filter(e -> e.getConfession()
						.equalsIgnoreCase(filters.getConfession()));
			}
			if (filters.getFood() != null) {
				System.out.println(filters.getFood());
				stream = stream
						.filter(e -> e.getFood().contains(filters.getFood()));
			}
		}

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
				.time(e.getTime())
				// .localDateTimeEvent(e.getLocalDateTimeEvent())
				.duration(e.getDuration()).address(e.getAddress())
				.food(e.getFood()).description(e.getDescription())
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

	// @Override
	// public Post getPost(String id) {
	// return repository.findById(id).orElse(null);
	// }
	//
	// @Override
	// public Post removePost(String id, String token) {
	// Post post = repository.findById(id).orElse(null);
	// if (post != null) {
	// repository.delete(post);
	// }
	// return post;
	// }
	//
	// @Override
	// public Post updatePost(PostUpdateDto postUpdateDto, String token) {
	// Post post = repository.findById(postUpdateDto.getId()).orElse(null);
	// if (post != null) {
	// post.setContent(postUpdateDto.getContent());
	// repository.save(post);
	// }
	// return post;
	// }
	//
	// @Override
	// public boolean addLike(String id) {
	// Post post = repository.findById(id).orElse(null);
	// if (post != null) {
	// post.addLike();
	// repository.save(post);
	// return true;
	// }
	// return false;
	// }
	//
	// @Override
	// public Post addComment(String id, NewCommentDto newComment) {
	// Post post = repository.findById(id).orElse(null);
	// if (post != null) {
	// Comment comment = new Comment(newComment.getUser(),
	// newComment.getMessage());
	// post.addComment(comment);
	// repository.save(post);
	// }
	// return post;
	// }
	//
	// @Override
	// public Iterable<Post> findPostsByTags(List<String> tags) {
	// return repository.findByTagsIn(tags);
	// }
	//
	// @Override
	// public Iterable<Post> findPostsByAuthor(String author) {
	// return repository.findByAuthor(author);
	// }
	//
	// @Override
	// public Iterable<Post> findPostsByDates(DatePeriodDto period) {
	// return
	// repository.findByDateCreatedBetween(LocalDate.parse(period.getFrom()),
	// LocalDate.parse(period.getTo()));
}
