package az.rahibjafar.msproduct.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    public static final String ORDERS_CREATED_TOPIC = "orders.created";
    public static final String STOCKS_RESERVED_TOPIC = "stocks.reserved";
    public static final String ORDERS_CANCELLED_TOPIC = "orders.cancelled";
    public static final String ORDERS_CREATED_DLT = "orders.created.DLT";
    public static final String STOCKS_RESERVED_DLT = "stocks.reserved.DLT";
    public static final String ORDERS_CANCELLED_DLT = "orders.cancelled.DLT";

    @Bean
    public NewTopic ordersCreatedTopic() {
        return new NewTopic(ORDERS_CREATED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic ordersCreatedDlt() {
        return new NewTopic(ORDERS_CREATED_DLT, 1, (short) 1);
    }

    @Bean
    public NewTopic stocksReservedTopic() {
        return new NewTopic(STOCKS_RESERVED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic stocksReservedDlt() {
        return new NewTopic(STOCKS_RESERVED_DLT, 1, (short) 1);
    }

    @Bean
    public NewTopic orderCancelledTopic() {
        return new NewTopic(ORDERS_CANCELLED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic orderCancelledDlt() {
        return new NewTopic(ORDERS_CANCELLED_DLT, 1, (short) 1);
    }
}
