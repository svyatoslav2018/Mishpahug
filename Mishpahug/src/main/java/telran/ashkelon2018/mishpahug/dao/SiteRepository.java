package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;

public interface SiteRepository extends MongoRepository<Event, String> {

	List<Event> findByDurationAndOwnerAndLocalDateTimeEvent(Integer duration, String owner, LocalDate date);

	// Iterable<Event> findAllEvents(String status);// status "In progress"
	//
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
