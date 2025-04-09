package pubsub.service;

@Service
public class UserSubscriber {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void subscribe() {
        Subscriber subscriber = Subscriber.newBuilder(
                SubscriptionName.of("your-gcp-project-id", "test-subscription"),
                (PubsubMessage message, AckReplyConsumer consumer) -> {
                    try {
                        String json = message.getData().toStringUtf8();
                        UserMessage user = objectMapper.readValue(json, UserMessage.class);
                        System.out.println("Received user: " + user.getName() + ", " + user.getEmail());
                        consumer.ack();
                    } catch (Exception e) {
                        consumer.nack();
                    }
                }
        ).build();
        subscriber.startAsync().awaitRunning();
    }
}
