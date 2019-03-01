package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import telran.ashkelon2018.mishpahug.dao.SiteRepository;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.Event;

import telran.ashkelon2018.mishpahug.domain.UserAccount;

import telran.ashkelon2018.mishpahug.dto.NewEventDto;
import telran.ashkelon2018.mishpahug.exceptions.WrongLoginOrPasswordException;

@Builder
@Service

public class SiteServiceImpl implements SiteService {

	@Autowired
	SiteRepository siteRepository;

	@Autowired
	UserAccountRepository userRepository;

	@Override
	public String addNewEvent(NewEventDto newEvent, String sessionLogin) {
		UserAccount userAccount = userRepository.findById(sessionLogin).get();
		if (!sessionLogin.equals(userAccount.getLogin())) {
			throw new WrongLoginOrPasswordException();// 401 unauthorized
		}
//		LocalDate date=LocalDate.now();
//		LocalTime time=LocalTime.now();
//		Event event=siteRepository.findById(event.getLogin()).get();
//		if(date==event.getDate()&&time==event.getTime())
//		{
//			throw new UserConflictException();//409 busy date
//		}
		
		System.out.println("newEventDto ==> " + newEvent);
		
		String eventId=userAccount.getLogin()+"D"+newEvent.getDate().toString().replaceAll("\\-", "")+"T"+newEvent.getTime().toString();
		Event event= Event.builder()
				.eventId(eventId)
				.owner(userAccount.getLogin())
				.title(newEvent.getTitle())
				.holiday(newEvent.getHoliday())
				.address(newEvent.getAddress())
				.confession(newEvent.getConfession())
				.date(newEvent.getDate())
				.time(newEvent.getTime())
				.duration(newEvent.getDuration())
				.food(newEvent.getFood())
				.description(newEvent.getDescription())
				.build();
		System.out.println(newEvent.getDate());
		System.out.println(newEvent.getTime());
		System.out.println("newEventToMongo ==> " + event);
		
		siteRepository.save(event);
		
		String res = "{'code': 200, 'message':'Event is created'}";
		
		return res;
		//return convertToNewEventDto(event);
	}
	
	
	
//	
//	private NewEventDto convertToNewEventDto(Event event) {
//		return NewEventDto.builder()
//				.title(event.getTitle())
//				.holiday(event.getHoliday())
//				.city(event.getCity())
//				.place_id(event.getPlace_id())
//				.location(event.getLocation())
//				.confession(event.getConfession())
//				.date(event.getDate())
//				.time(event.getTime())
//				.duration(event.getDuration())
//				.food(event.getFood())
//				.description(event.getDescription())
//				.build();
//	}
	
	
	
	
//	private Event convertToEvent(NewEventDto newEvent) {
//		return new Event(newEvent.getLogin(), newEvent.getEventId(),newEvent.getTitle(),
//				newEvent.getHoliday(),newEvent.getAddress(), newEvent.getEventConfession(), newEvent.getDate(),
//				newEvent.getTime(),newEvent.getDuration(),newEvent.getFood(),newEvent.getDescription());
//	}

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
