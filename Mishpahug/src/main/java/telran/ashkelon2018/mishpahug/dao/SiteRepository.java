package telran.ashkelon2018.mishpahug.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;

public interface SiteRepository extends MongoRepository<Event, String> {
	
//	Iterable<Event> findByTagsIn(List<String> tags);
//
//	Iterable<Event> findByAuthor(String author);
//
//	Iterable<Event> findByDateBetween(LocalDate from, LocalDate to);

}
