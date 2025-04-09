package pubsub.subscriber;

import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.spring.pubsub.core.subscriber.PubSubSubscriberTemplate;
import com.google.cloud.spring.pubsub.support.SubscriberFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConfig {

    @Bean
    public MessageReceiver messageReceiver() {
        return (message, consumer) -> {
            String payload = message.getData().toStringUtf8();
            System.out.println("Received message: " + payload);
            consumer.ack(); // or consumer.nack();
        };
    }

    @Bean
    public SubscriberFactory subscriberFactory(
            PubSubSubscriberTemplate pubSubSubscriberTemplate,
            MessageReceiver messageReceiver
    ) {
        pubSubSubscriberTemplate.subscribe("test-subscription", messageReceiver);
        return new SubscriberFactory();
    }
}
