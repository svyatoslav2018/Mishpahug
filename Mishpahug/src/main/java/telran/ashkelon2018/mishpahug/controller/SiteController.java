package telran.ashkelon2018.mishpahug.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DatePeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;
import telran.ashkelon2018.forum.service.ForumService;
import telran.ashkelon2018.mishpahug.dto.NewEventDto;

@RestController
@RequestMapping("/forum") // all will be start from account

public class SiteController {
	@Autowired
	ForumService service;

	@PostMapping("/event")
	public Post addEvent(@RequestBody NewEventDto newEvent) {
		return service.addNewPost(newEvent);
	}

	@GetMapping("/event/{id}")
	public Post getEvent(@PathVariable String id) {
		return service.getEvent(id);
	}

	@DeleteMapping("/event/{id}")
	public Post removeEvent(@PathVariable String id, @RequestHeader("Authorization") String token) {
		return service.removeEvent(id, token);
	}

	@PutMapping("/event/{id}/like")
	public boolean addRating(@PathVariable String id) {
		return service.addRating(id);
	}

	// @PutMapping("/event/{id}/comment")
	// public Post addComment(@PathVariable String id,
	// @RequestBody NewCommentDto newCommentDto) {
	// return service.addComment(id, newCommentDto);
	// }

	// this methods can use all users without authentification

	@PostMapping("/events/city")
	public Iterable<Event> getEventsByCity(@RequestBody Set<String> city) {
		return service.getEventsByCity(city);
	}

	@PostMapping("/events/period")
	public Iterable<Event> getEventsBetweenDate(@RequestBody DatePeriodDto periodDto) {
		return service.findEventsByDates(periodDto);
	}

	@PostMapping("/events/holiday")
	public Iterable<Event> getEventsByHoliday(@RequestBody Set<String> holiday) {
		return service.getEventsByHoliday(holiday);
	}
	
	@PostMapping("/events/confession")
	public Iterable<Event> getEventsByConfession(@RequestBody Set<String> confession) {
		return service.getEventsByConfession(confession);
	}
	
	@PostMapping("/events/foodPref")
	public Iterable<Event> getEventsByFoodPref(@RequestBody Set<String> foodPreference) {
		return service.getEventsByFoodPref(foodPreference);
	}
	
}
