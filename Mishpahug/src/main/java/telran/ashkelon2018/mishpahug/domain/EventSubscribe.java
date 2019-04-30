package telran.ashkelon2018.mishpahug.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "EventSubscribe")
@CompoundIndexes(value = {
		@CompoundIndex(name = "event_subscriber_id", def = "{'eventId':1, 'subscriberId':1}", unique = true) })
@Builder
@Getter
public class EventSubscribe {
	@NotEmpty
	String eventId;
	@NotEmpty
	String subscriberId;
	Boolean isInvited;

	public EventSubscribe(String eventId, String subscriberId) {
		this.eventId = eventId;
		this.subscriberId = subscriberId;
	}
	
	}
