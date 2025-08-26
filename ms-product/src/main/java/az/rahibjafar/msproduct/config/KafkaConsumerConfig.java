package az.rahibjafar.msproduct.config;

import az.rahibjafar.msproduct.event.model.OrderCreatedEvent;
import az.rahibjafar.msproduct.event.model.StockRollbackEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> orderCreatedEventFactory(
            ConsumerFactory<String, OrderCreatedEvent> cf
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent>();
        factory.setConsumerFactory(cf);

        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

        factory.setConcurrency(2);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StockRollbackEvent> stocksRollbackEventFactory(
            ConsumerFactory<String, StockRollbackEvent> cf
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, StockRollbackEvent>();
        factory.setConsumerFactory(cf);

        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

        factory.setConcurrency(2);

        return factory;
    }
}
