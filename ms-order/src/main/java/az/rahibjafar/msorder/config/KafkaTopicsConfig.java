package az.rahibjafar.msorder.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    public static final String ORDERS_CREATED_TOPIC = "orders.created";
    public static final String STOCKS_RESERVED_TOPIC = "stocks.reserved";
    public static final String ORDERS_CANCELLED_TOPIC = "orders.cancelled";
    public static final String PAYMENT_COMPLETED_TOPIC = "payment.completed";
    public static final String PAYMENT_CANCELLED_TOPIC = "payment.cancelled";
    public static final String STOCKS_ROLLBACK_TOPIC = "stocks.rollback";
    public static final String STOCKS_RESERVED_DLT = "stocks.reserved.DLT";
    public static final String ORDERS_CANCELLED_DLT = "orders.cancelled.DLT";
    public static final String PAYMENT_COMPLETED_DLT = "payment.completed.DLT";
    public static final String PAYMENT_CANCELLED_DLT = "payment.cancelled.DLT";
    public static final String STOCKS_ROLLBACK_DLT = "stocks.rollback.DLT";

    @Bean
    public NewTopic ordersCreatedTopic() {
        return new NewTopic(ORDERS_CREATED_TOPIC, 1, (short) 1);
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

    @Bean
    public NewTopic paymentCompletedTopic() {
        return new NewTopic(PAYMENT_COMPLETED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCompletedDlt() {
        return new NewTopic(PAYMENT_COMPLETED_DLT, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCancelledTopic() {
        return new NewTopic(PAYMENT_CANCELLED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCancelledDlt() {
        return new NewTopic(PAYMENT_CANCELLED_DLT, 1, (short) 1);
    }

    @Bean
    public NewTopic stocksRollbackTopic() {
        return new NewTopic(STOCKS_ROLLBACK_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic stocksRollbackDlt() {
        return new NewTopic(STOCKS_ROLLBACK_DLT, 1, (short) 1);
    }
}
