package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.mishpahug.dao.SiteRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.dto.DatePeriodDto;
import telran.ashkelon2018.mishpahug.dto.NewEventDto;

@Service
public class SiteServiceImpl implements SiteService{

	@Autowired
	SiteRepository siteRepository;

	@Override
	public Event addNewEvent(NewEventDto newEvent,String token) {
		Event event = convertToEvent(newEvent);
		return siteRepository.save(event);
	}

	private Event convertToEvent(NewEventDto newEvent) {
		return new Event(newEvent.getTitle(), newEvent.getHoliday(), newEvent.getAddress(),
				newEvent.getEventConfession(),newEvent.getDate(),newEvent.getTime(),
				newEvent.getDuration(),newEvent.getFoodPreference(),newEvent.getDescription());
	}

	@Override
	public Event getEvent(String login, LocalDate date, LocalTime time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event removeEvent(String status, String login, LocalDate date, LocalTime time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addRating(String login, LocalDate date, LocalTime time) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Event> findAllEvents(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findEventsByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findEventsByDates(DatePeriodDto periodDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findEventsByHoliday(String holiday) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findEventsByConfession(String confession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findEventsByFoodPref(String foodPreference) {
		// TODO Auto-generated method stub
		return null;
	}


	

//	@Override
//	public Post getPost(String id) {
//		return repository.findById(id).orElse(null);
//	}
//
//	@Override
//	public Post removePost(String id, String token) {
//		Post post = repository.findById(id).orElse(null);
//		if (post != null) {
//			repository.delete(post);
//		}
//		return post;
//	}
//
//	@Override
//	public Post updatePost(PostUpdateDto postUpdateDto, String token) {
//		Post post = repository.findById(postUpdateDto.getId()).orElse(null);
//		if (post != null) {
//			post.setContent(postUpdateDto.getContent());
//			repository.save(post);
//		}
//		return post;
//	}
//
//	@Override
//	public boolean addLike(String id) {
//		Post post = repository.findById(id).orElse(null);
//		if (post != null) {
//			post.addLike();
//			repository.save(post);
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public Post addComment(String id, NewCommentDto newComment) {
//		Post post = repository.findById(id).orElse(null);
//		if (post != null) {
//			Comment comment = new Comment(newComment.getUser(), newComment.getMessage());
//			post.addComment(comment);
//			repository.save(post);
//		}
//		return post;
//	}
//
//	@Override
//	public Iterable<Post> findPostsByTags(List<String> tags) {
//		return repository.findByTagsIn(tags);
//	}
//
//	@Override
//	public Iterable<Post> findPostsByAuthor(String author) {
//		return repository.findByAuthor(author);
//	}
//
//	@Override
//	public Iterable<Post> findPostsByDates(DatePeriodDto period) {
//		return repository.findByDateCreatedBetween(LocalDate.parse(period.getFrom()), LocalDate.parse(period.getTo()));
	}

}
