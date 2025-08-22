package az.rahibjafar.msorder.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    public static final String ORDERS_CREATED_TOPIC = "orders.created";

    @Bean
    public NewTopic ordersCreatedTopic() {
        return new NewTopic(ORDERS_CREATED_TOPIC, 1, (short) 1);
    }
}
