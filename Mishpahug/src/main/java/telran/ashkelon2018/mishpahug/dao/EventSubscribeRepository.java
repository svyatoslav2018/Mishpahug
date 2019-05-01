package telran.ashkelon2018.mishpahug.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.EventSubscribe;

public interface EventSubscribeRepository extends MongoRepository<EventSubscribe, String> {

	List<EventSubscribe> findByEventId(String eventId);
}
