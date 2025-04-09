package pubsub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pubsub.model.UserMessage;

@RestController
@RequestMapping("/api/pubsub")
public class PubSubController {

    private final PubSubTemplate pubSubTemplate;

    @Value("${gcp.pubsub.topic}")
    private String topicName;

    public PubSubController(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody String message) {
        pubSubTemplate.publish(topicName, message);
        return ResponseEntity.ok("Message published: " + message);
    }
    @PostMapping("/publish/json")
    public ResponseEntity<String> publishJson(@RequestBody UserMessage message) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(message);
        pubSubTemplate.publish(topicName, json);
        return ResponseEntity.ok("JSON message published");
    }
}

