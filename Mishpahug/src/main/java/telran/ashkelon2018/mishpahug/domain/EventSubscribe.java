package telran.ashkelon2018.mishpahug.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "EventSubscribe")
@CompoundIndexes(value = {
		@CompoundIndex(name = "event_subscriber_id", def = "{'eventId':1, 'subscriberId':1}", unique = true) })

@Builder
@Getter
@ToString
public class EventSubscribe {

	String _id;
	@NotEmpty
	String eventId;
	@NotEmpty
	String subscriberId;
	@Setter
	Boolean isInvited;
	@Setter
	Boolean voted;
	@Setter
	Double rate;

	public EventSubscribe(String eventId, String subscriberId) {
		this.eventId = eventId;
		this.subscriberId = subscriberId;
	}

	public EventSubscribe(String eventId, String subscriberId, boolean isInvited, boolean voted, Double rate) {
		this.eventId = eventId;
		this.subscriberId = subscriberId;
		this.isInvited = isInvited;
		this.voted = voted;
		this.rate = rate;

	}

}
