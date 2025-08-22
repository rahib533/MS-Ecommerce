package az.rahibjafar.msorder.config;

import az.rahibjafar.msorder.event.model.OrderCancelledEvent;
import az.rahibjafar.msorder.event.model.StockReservedEvent;
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
    public ConcurrentKafkaListenerContainerFactory<String, OrderCancelledEvent> orderCancelledEventFactory(
            ConsumerFactory<String, OrderCancelledEvent> cf
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderCancelledEvent>();
        factory.setConsumerFactory(cf);

        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

        factory.setConcurrency(2);

        return factory;
    }
}
