package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;

public interface EventsRepository extends MongoRepository<Event, String> {

	List<Event> findByDurationAndOwnerAndDateAndTime(Integer duration, String owner, LocalDate date,LocalTime time);
	
	//EventListResponseDto findByEventId(String EventId);
	//Page<Event> findByLocationNear(Point point, Distance distance);
	//LocationNearAndEventStatusEquals(Point point, Distance distance, String eventStatus, Pageable pageable);
	//EventListResponseDto findByEventStatus(String eventStatus);// status "In progress"
	
	Page<Event>findByEventStatus(String eventStatus,Pageable pageable);
	
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
