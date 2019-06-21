package telran.ashkelon2018.mishpahug.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.ashkelon2018.mishpahug.dto.FrequencyLoginDto;

@EnableBinding(Processor.class)
public class ProducerService {

	ObjectMapper mapper = new ObjectMapper();
	@Value("${minId:1}")
	int minId;
	@Value("${maxId:10}")
	int maxId;

	@Autowired()
	Processor messSensor;
	public void sensorData(String username, long timestamp)
			throws JsonProcessingException {

		int id = getRandomId();
		messSensor.output().send(
				MessageBuilder.withPayload(getPayLoad(id, username, timestamp)).build());
	}

	private String getPayLoad(int id, String username, long timestamp)
			throws JsonProcessingException {
		FrequencyLoginDto frequencyLogin = new FrequencyLoginDto(id, username, timestamp);
		return mapper.writeValueAsString(frequencyLogin);
	}

	private int getRandomId() {
		return getRandomNumber(minId, maxId);
	}

	private int getRandomNumber(int min, int max) {
		return (int) (min + Math.random() * (max + 1 - min));
	}

}
