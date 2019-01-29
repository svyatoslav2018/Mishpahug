package telran.ashkelon2018.mishpahug.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mishpahug") // all will be start from account

public class SiteController {
//	@Autowired
//	SiteService service;

//	@PostMapping("/event")
//	public Event addEvent(@RequestBody NewEventDto newEvent) {
//		return service.addNewEvent(newEvent);
//	}
//
//	@GetMapping("/event/{email,dateCreated}")// id=email,dateCreated
//	public Event getEvent(@PathVariable String email, @PathVariable LocalDateTime dateCreated) {
//		return service.getEvent(email, dateCreated);
//	}
//
//	//i think delete must be automaticaly after "done"
////	@DeleteMapping("/event/{email,dateCreated}") // id=email,dateCreated 
////	public Event removeEvent(@PathVariable String email, @PathVariable LocalDateTime dateCreated,
////			@RequestBody String token) {
////		return service.removeEvent(email, dateCreated, token);
////	}
//
//	@PutMapping("/event/{email,dateCreated}/rate")
//	public boolean addRating(@PathVariable String email, @PathVariable LocalDateTime dateCreated) {
//		return service.addRating(email, dateCreated);
//	}
//
//	// this methods can use all users without authentification
//
//	@PostMapping("/events/city")//how get city from address
//	public Iterable<Event> getEventsByCity(@PathVariable String city) {
//		return service.findEventsByCity(city);
//	}
//
//	@PostMapping("/events/period")
//	public Iterable<Event> getEventsBetweenDates(@RequestBody DatePeriodDto periodDto) {
//		return service.findEventsByDates(periodDto);
//	}
//
//	@PostMapping("/events/holiday")
//	public Iterable<Event> getEventsByHoliday(@PathVariable String holiday) {
//		return service.findEventsByHoliday(holiday);
//	}
//
//	@PostMapping("/events/confession")
//	public Iterable<Event> getEventsByConfession(@PathVariable String confession) {
//		return service.findEventsByConfession(confession);
//	}
//
//	@PostMapping("/events/foodPref")
//	public Iterable<Event> getEventsByFoodPref(@PathVariable String foodPreference) {
//		return service.findEventsByFoodPref(foodPreference);
//	}

}
