package az.rahibjafar.mspayment.config;

import az.rahibjafar.mspayment.event.model.PaymentCancelledEvent;
import az.rahibjafar.mspayment.event.model.PaymentCompletedEvent;
import az.rahibjafar.mspayment.event.model.StockReservedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StockReservedEvent> stockReservedEventFactory(
            ConsumerFactory<String, StockReservedEvent> cf
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, StockReservedEvent>();
        factory.setConsumerFactory(cf);

        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

        factory.setConcurrency(2);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent> paymentCompletedEventFactory(
            ConsumerFactory<String, PaymentCompletedEvent> cf
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent>();
        factory.setConsumerFactory(cf);

        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

        factory.setConcurrency(2);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCancelledEvent> paymentCancelledEventFactory(
            ConsumerFactory<String, PaymentCancelledEvent> cf
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentCancelledEvent>();
        factory.setConsumerFactory(cf);

        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

        factory.setConcurrency(2);

        return factory;
    }

}
