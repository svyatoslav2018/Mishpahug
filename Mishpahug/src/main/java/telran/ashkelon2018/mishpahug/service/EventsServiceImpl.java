package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.configuration.EventConfiguration;
import telran.ashkelon2018.mishpahug.dao.EventsRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventDto;
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

	@Override
	public CodeResponseDto addNewEvent(EventDto newEvent, String sessionLogin) {
		UserAccount userAccount = userRepository.findById(sessionLogin).get();
		if (!sessionLogin.equals(userAccount.getLogin())) {
			throw new WrongLoginOrPasswordException();// 401 unauthorized
		}
		LocalDateTime dateFrom = newEvent.getDate().atTime(newEvent.getTime());
		LocalDateTime dateTo = dateFrom.plusHours(newEvent.getDuration());
		LocalDateTime checkDateFrom = LocalDateTime.of(newEvent.getDate(), newEvent.getTime());
		LocalDateTime checkDateTo = checkDateFrom.plusMinutes(newEvent.getDuration());
		boolean checktime1 = LocalDateTime.now().isBefore(dateFrom.minusHours(48));
		boolean checktime2 = LocalDateTime.now().isAfter(dateFrom.minusMonths(2));
		boolean checktime3 = false;

		List<Event> list = eventsRepository.findByDurationAndOwnerAndLocalDateTimeEvent(newEvent.getDuration(),
				newEvent.getOwner(), newEvent.getDate());
		if (list.isEmpty()) {
			checktime3 = true;
		}
		if (!list.isEmpty()) {
			checktime3 = true;
			for (Event event : list) {
				if (!event.getLocalDateTimeEvent().isBefore(dateFrom)
						&& !dateTo.isBefore(event.getLocalDateTimeEvent())) {
					checktime3 = false;
				}
			}
		}
		if (!(checktime1 && checktime2 && checktime3)) {
			//throw new UnprocessableEntityException();// 422 Invalid data
			return new CodeResponseDto(422, "Invalid data");
		}

		String eventId = userAccount.getLogin() + "D" + newEvent.getDate().toString().replaceAll("\\-", "") + "T"
				+ newEvent.getTime().toString();
		if (eventsRepository.findById(eventId).orElse(null) != null) {
			throw new UserConflictException();// 409 busy date
		}

		LocalDateTime localDateTimeEvent = LocalDateTime.of(newEvent.getDate(), newEvent.getTime());
		Event event = Event.builder().eventId(eventId).owner(userAccount.getLogin()).title(newEvent.getTitle())
				.holiday(newEvent.getHoliday()).address(newEvent.getAddress()).confession(newEvent.getConfession())
				.localDateTimeEvent(localDateTimeEvent).duration(newEvent.getDuration()).food(newEvent.getFood())
				.description(newEvent.getDescription())
				//.status(newEvent.getStatus())
				.build();
		//event.setStatus(eventConfiguration.INPROGRESS);
		eventsRepository.save(event);
		return new CodeResponseDto(200, "Event is created");
	}

	@Override
	public Iterable<Event> findEventsInProgressUnAuth(String status, EventDto eventDto, Integer page, Integer size,
			String sessionLogin) {
		
		if (sessionLogin != null) {
			return null;
		}
		if(!status.equals(eventConfiguration.INPROGRESS)){
			return null;
		}

		return eventsRepository.findByStatus(status);
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
	// Comment comment = new Comment(newComment.getUser(), newComment.getMessage());
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
	// return repository.findByDateCreatedBetween(LocalDate.parse(period.getFrom()),
	// LocalDate.parse(period.getTo()));
}
