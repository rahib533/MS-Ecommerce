package az.rahibjafar.mscustomer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    public static final String PAYMENT_CREATED_TOPIC = "payment.created";
    public static final String PAYMENT_COMPLETED_TOPIC = "payment.completed";
    public static final String PAYMENT_CANCELLED_TOPIC = "payment.cancelled";
    public static final String PAYMENT_CREATED_DLT = "payment.created.DLT";
    public static final String PAYMENT_COMPLETED_DLT = "payment.completed.DLT";
    public static final String PAYMENT_CANCELLED_DLT = "payment.cancelled.DLT";

    @Bean
    public NewTopic paymentCreatedTopic() {
        return new NewTopic(PAYMENT_CREATED_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCreatedDlt() {
        return new NewTopic(PAYMENT_CREATED_DLT, 1, (short) 1);
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
}
