package az.rahibjafar.mscustomer.config;

import az.rahibjafar.mscustomer.event.model.PaymentCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent> paymentCreatedEventFactory(
            ConsumerFactory<String, PaymentCreatedEvent> cf
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent>();
        factory.setConsumerFactory(cf);

        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

        factory.setConcurrency(2);

        return factory;
    }
}
