package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;

public interface EventsRepository extends MongoRepository<Event, String> {

	List<Event> findByDurationAndOwnerAndDateAndTime(Integer duration,
			String owner, LocalDate date, LocalTime time);


	
////////////////////////////////changed by mongotempate//////////////////////////////////////////////////////////
	Page<Event> findByEventStatus(String eventStatus, Pageable pageable);
	// @Query("{'holiday' : '8 march', 'confession': 'Religious'}")
	Page<Event> findByEventStatusAndHolidayAndConfessionAndFoodAndDateBetween(
			String eventStatus, String holiday, String confession, String food,
			LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

	Page<Event> findByEventStatusAndDateBetween(String eventStatus,
			LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

	Page<Event> findByEventStatusAndHolidayAndConfessionAndFood(
			String eventStatus, String holiday, String confession, String food,
			Pageable pageable);

	Page<Event> findByEventStatusAndHoliday(String eventStatus, String holiday,
			Pageable pageable);

	Page<Event> findByEventStatusAndConfession(String eventStatus,
			String confession, Pageable pageable);

	Page<Event> findByEventStatusAndFood(String eventStatus, String food,
			Pageable pageable);

	// Iterable<Event> findEventsByCity(String city);
	//
	// Iterable<Event> findByDateBetween(LocalDate dateFrom, LocalDate dateTo,
	// Pageable pageable);
	//
	// Iterable<Event> findEventsByHoliday(String holiday);
	//
	// Iterable<Event> findEventsByConfession(String confession);
	//
	// Iterable<Event> findEventsByFoodPref(String foodPreference);

}
