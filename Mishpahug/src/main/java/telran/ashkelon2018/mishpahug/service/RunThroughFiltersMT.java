package telran.ashkelon2018.mishpahug.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.mishpahug.configuration.EventConfiguration;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.Filters;
import telran.ashkelon2018.mishpahug.domain.Location;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.exceptions.UnprocessableEntityException;

@Service
public class RunThroughFiltersMT {

	@Autowired
	MongoTemplate mongoTemplate;


	public Page<Event> madeListWithFilter(EventListRequestDto body,
			Pageable pageable) {
		String eventStatus = EventConfiguration.INPROGRESS;
		Query query = new Query(Criteria.where("eventStatus").is(eventStatus));
		query.with(pageable);

		Filters filters = body.getFilters();
//		Principal principal = null;
//		String sessionLogin = principal.getName();
//		System.out.println("Events without events where owner= " + sessionLogin);
//		if (sessionLogin != null) {
//			query.addCriteria(Criteria.where("owner").ne(sessionLogin));
//		}
		if (filters.getDateFrom() != null) {
			if (filters.getDateFrom().isBefore(LocalDate.now())) {
				throw new UnprocessableEntityException(422, "Invalid filter parameters!");
			}
		}

		if (filters.getDateFrom() != null && filters.getDateTo() == null) {
			System.out.println(filters.getDateFrom());
			query.addCriteria(
					Criteria.where("date").gte(filters.getDateFrom()));
		}

		if (filters.getDateFrom() == null && filters.getDateTo() != null) {
			System.out.println(filters.getDateTo());
			query.addCriteria(Criteria.where("date").lte(filters.getDateTo()));
		}
		if (filters.getDateFrom() != null && filters.getDateTo() != null) {
			System.out.println(
					filters.getDateFrom() + "-:-" + filters.getDateTo());
			query.addCriteria(Criteria.where("date").gte(filters.getDateFrom())
					.lte(filters.getDateTo()));
		}

		if (filters.getHolidays() != null) {
			System.out.println(filters.getHolidays());
			query.addCriteria(
					Criteria.where("holiday").is(filters.getHolidays()));
		}
		if (filters.getConfession() != null) {
			System.out.println(filters.getConfession());
			query.addCriteria(
					Criteria.where("confession").is(filters.getConfession()));
		}
		if (filters.getFood() != null) {
			System.out.println(filters.getFood());
			query.addCriteria(Criteria.where("food").is(filters.getFood()));
		}
		Location location = body.getLocation();
		if (location.getLng() != null && location.getLat() != null
				&& location.getRadius() != null) {
			System.out.println(location.getLng() + " " + location.getLat() + " "
					+ location.getRadius());

			Point point = new Point(location.getLng(), location.getLat());
			Circle circle = new Circle(point,
					location.getRadius() / 1000 / 6378.1); // meters to
															// kilometers and to
															// radians
			query.addCriteria(
					Criteria.where("address.location").withinSphere(circle));

		}

		List<Event> qlistOfEvents = mongoTemplate.find(query, Event.class);
		// System.out.println("found events: " + qlistOfEvents.size());

		return PageableExecutionUtils.getPage(qlistOfEvents, pageable,
				() -> mongoTemplate.count(query, Event.class));

	}

}
