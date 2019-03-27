package telran.ashkelon2018.mishpahug.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.mishpahug.domain.EventSubscribe;

public interface EventSubscribeRepository extends MongoRepository<EventSubscribe, String> {

}
