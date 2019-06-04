package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.EventSubscribe;
import telran.ashkelon2018.mishpahug.domain.UserAccount;

public interface EventsRepository extends MongoRepository<Event, String> {

	List<Event> findByDurationAndOwnerAndDateAndTime(Integer duration, String owner, LocalDate date, LocalTime time);

	Page<Event> findByEventStatus(String eventStatus, Pageable pageable);

	// @Query("{'holiday' : '8 march', 'confession': 'Religious'}")
	Page<Event> findByEventStatusAndHolidayAndConfessionAndFoodAndDateBetween(String eventStatus, String holiday,
			String confession, String food, LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

	Page<Event> findByEventStatusAndDateBetween(String eventStatus, LocalDate dateFrom, LocalDate dateTo,
			Pageable pageable);

	Page<Event> findByEventStatusAndHolidayAndConfessionAndFood(String eventStatus, String holiday, String confession,
			String food, Pageable pageable);

	Page<Event> findByEventStatusAndHoliday(String eventStatus, String holiday, Pageable pageable);

	Page<Event> findByEventStatusAndConfession(String eventStatus, String confession, Pageable pageable);

	Page<Event> findByEventStatusAndFood(String eventStatus, String food, Pageable pageable);

	Page<Event> findByOwnerAndEventStatus(String owner, String eventStatus, Pageable pageable);

	List<Event> findByOwnerAndEventStatus(String sessionLogin, String inprogress);

	List<Event> findByOwner(String sessionLogin);

	List<Event> findByEventId(String eventId);

	List<Event> findByEventIdAndEventStatus(String sessionLogin, String inprogress);

	Event findByOwnerAndEventId(String sessionLogin, String eventId);
	
	Event findByEventId(String eventId,String sessionLogin);

	List<Event> findByEventId(String eventId, Pageable pageable);

	List<Event> findByEventId(List<String> listId);


}
