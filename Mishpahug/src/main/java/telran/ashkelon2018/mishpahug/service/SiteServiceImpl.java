package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.dao.SiteRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.NewEventDto;
import telran.ashkelon2018.mishpahug.exceptions.UnprocessableEntityException;
import telran.ashkelon2018.mishpahug.exceptions.WrongLoginOrPasswordException;

@Service
@Builder
public class SiteServiceImpl implements SiteService {

	@Autowired
	SiteRepository siteRepository;

	@Autowired
	UserAccountRepository userRepository;

	@Override
	public CodeResponseDto addNewEvent(NewEventDto newEvent, String sessionLogin) {
		UserAccount userAccount = userRepository.findById(sessionLogin).get();
		if (!sessionLogin.equals(userAccount.getLogin())) {
			throw new WrongLoginOrPasswordException();// 401 unauthorized
		}
//		LocalDateTime dateFrom = newEvent.getDate().atTime(newEvent.getTime());
//		LocalDateTime dateTo = dateFrom.plusHours(newEvent.getDuration());
//		LocalDateTime checkDateFrom = newEvent.getDate().atTime(LocalTime.of(0, 0, 0));
//		LocalDateTime checkDateTo = checkDateFrom.plusDays(1);
//		boolean checktime1 = LocalDateTime.now().isBefore(dateFrom.minusHours(48));
//		boolean checktime2 = LocalDateTime.now().isAfter(dateFrom.minusMonths(2));
//		boolean checktime3 = false;

//		List<Event> list = siteRepository.findByDateFromBetweenAndOwnerIn(checkDateFrom.toLocalDate(),
//				checkDateTo.toLocalDate(), sessionLogin);
//		if (list.isEmpty()) {
//			checktime3 = true;
//		}
		// if (!list.isEmpty()) {
		// checktime3 = true;
		// for (Event event : list) {
		// if (!event.getDate().isBefore(dateFrom)
		// && !dateFrom.plusHours(newEvent.getDuration()).isBefore(event.getDate())) {
		// checktime3 = false;
		// }
		// }
		// }
//		if (!(checktime1 && checktime2 && checktime3)) {
//			throw new UnprocessableEntityException();// 422 Invalid data
//		}

		String eventId = userAccount.getLogin() + "D" + newEvent.getDate().toString().replaceAll("\\-", "") + "T"
				+ newEvent.getTime().toString();

		// if (siteRepository.findById(eventId).orElse(null) != null) {
		// throw new UserConflictException();
		// CodeResponseDto codeResponseDto=new CodeResponseDto(409,"Hello");// 409 busy
		// date
		// }

		LocalDateTime localDateTimeCreation = LocalDateTime
				.of(newEvent.getDate(), newEvent.getTime());
		Event event = Event.builder().eventId(eventId)
				.owner(userAccount.getLogin()).title(newEvent.getTitle())
				.holiday(newEvent.getHoliday()).address(newEvent.getAddress())
				.confession(newEvent.getConfession())
				.dateTimeCreation(localDateTimeCreation)
				// .time(newEvent.getTime())
				.duration(newEvent.getDuration()).food(newEvent.getFood())
				.description(newEvent.getDescription()).build();
		siteRepository.save(event);
		return new CodeResponseDto(200, "Event is created");
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
