package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;

public interface StaticFieldsRepository extends MongoRepository<Event, String> {
	
	Iterable<Event> findByTagsIn(List<String> tags);

	Iterable<Event> findByAuthor(String author);

	Iterable<Event> findByDateBetween(LocalDate from, LocalDate to);

}
