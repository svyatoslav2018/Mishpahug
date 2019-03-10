package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;

public interface EventsRepository extends MongoRepository<Event, String> {

	List<Event> findByDurationAndOwnerAndDateAndTime(Integer duration, String owner, LocalDate date);

	Iterable<Event> findByEventStatus(String eventStatus);// status "In progress"
	
	// Iterable<Event> findEventsByCity(String city);
	//
	// Iterable<Event> findByDateBetween(LocalDate dateFrom, LocalDate dateTo);
	//
	// Iterable<Event> findEventsByHoliday(String holiday);
	//
	// Iterable<Event> findEventsByConfession(String confession);
	//
	// Iterable<Event> findEventsByFoodPref(String foodPreference);

}
