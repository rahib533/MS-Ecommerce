package az.rahibjafar.msproduct.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    public static final String ORDERS_CREATED_TOPIC = "orders.created";
    public static final String ORDERS_CREATED_DLT = "orders.created.DLT";

    @Bean
    public NewTopic ordersCreatedTopic() {
        return new NewTopic(ORDERS_CREATED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic ordersCreatedDlt() {
        return new NewTopic(ORDERS_CREATED_DLT, 1, (short) 1);
    }
}
