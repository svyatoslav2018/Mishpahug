package telran.ashkelon2018.mishpahug.dao;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.Event;

public interface SiteRepository extends MongoRepository<Event, String> {

	Iterable<Event> findAllEvents(String status);// status "In progress"

	Iterable<Event> findEventsByCity(String city);

	Iterable<Event> findByDateBetween(LocalDate dateFrom, LocalDate dateTo);

	Iterable<Event> findEventsByHoliday(String holiday);

	Iterable<Event> findEventsByConfession(String confession);

	Iterable<Event> findEventsByFoodPref(String foodPreference);

}
