package telran.ashkelon2018.mishpahug.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.Getter;
import telran.ashkelon2018.mishpahug.configuration.EventConfiguration;
import telran.ashkelon2018.mishpahug.dao.EventsRepository;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.domain.Filters;
import telran.ashkelon2018.mishpahug.exceptions.UnprocessableEntityException;

@Service
public class RunThroughFiltersMR {
	@Autowired
	EventsRepository eventsRepository;
	@Getter
	boolean standartFilter;

	public Page<Event> madeListWithFilter(Filters filters, Pageable pageable) {
		
		String eventStatus = EventConfiguration.INPROGRESS;

		if (filters.getDateFrom() != null) {
			if (filters.getDateFrom().isBefore(LocalDate.now())) {
				throw new UnprocessableEntityException();// "code": 422,
															// "message":
															// "Invalid filter
															// parameters!"
			}
		}

		if (filters.getHolidays() != null && filters.getConfession() != null
				&& filters.getFood() != null && filters.getDateFrom() != null
				&& filters.getDateTo() != null) {
			System.out.println("full filter: ");
			standartFilter = true;
			return eventsRepository
					.findByEventStatusAndHolidayAndConfessionAndFoodAndDateBetween(
							eventStatus, filters.getHolidays(),
							filters.getConfession(), filters.getFood(),
							filters.getDateFrom(), filters.getDateTo(),
							pageable);
		}
		if (filters.getHolidays() == null && filters.getConfession() == null
				&& filters.getFood() == null && filters.getDateFrom() != null
				&& filters.getDateTo() != null) {
			System.out.println("date filter: ");
			standartFilter = true;
			return eventsRepository.findByEventStatusAndDateBetween(eventStatus,
					filters.getDateFrom(), filters.getDateTo(), pageable);
		}
		if (filters.getHolidays() != null && filters.getConfession() != null
				&& filters.getFood() != null && filters.getDateFrom() == null
				&& filters.getDateTo() == null) {
			System.out.println("without date filter: ");
			standartFilter = true;
			return eventsRepository
					.findByEventStatusAndHolidayAndConfessionAndFood(
							eventStatus, filters.getHolidays(),
							filters.getConfession(), filters.getFood(),
							pageable);
		}
		if (filters.getHolidays() != null && filters.getConfession() == null
				&& filters.getFood() == null && filters.getDateFrom() == null
				&& filters.getDateTo() == null) {
			System.out.println("Hholiday filter: ");
			standartFilter = true;
			return eventsRepository.findByEventStatusAndHoliday(
					eventStatus, filters.getHolidays(), pageable);
		}
		if (filters.getHolidays() == null && filters.getConfession() != null
				&& filters.getFood() == null && filters.getDateFrom() == null
				&& filters.getDateTo() == null) {
			System.out.println("Confession filter: ");
			standartFilter = true;
			return eventsRepository.findByEventStatusAndConfession(
					eventStatus, filters.getConfession(), pageable);
		}
		if (filters.getHolidays() == null && filters.getConfession() == null
				&& filters.getFood() != null && filters.getDateFrom() == null
				&& filters.getDateTo() == null) {
			System.out.println("Food filter: ");
			standartFilter = true;
			return eventsRepository.findByEventStatusAndFood(
					eventStatus, filters.getFood(), pageable);
		} 

			System.out.println("no filter, full list: ");
			standartFilter = false;
			return eventsRepository.findByEventStatus(eventStatus,
					pageable);

		

	}

}
