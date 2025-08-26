package az.rahibjafar.msorder.event.producer;

import az.rahibjafar.msorder.config.KafkaTopicsConfig;
import az.rahibjafar.msorder.event.model.OrderCancelledEvent;
import az.rahibjafar.msorder.event.model.OrderCreatedEvent;
import az.rahibjafar.msorder.event.model.StockRollbackEvent;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;


@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(KafkaTopicsConfig.ORDERS_CREATED_TOPIC, event.orderId().toString(), event);
    }

    public void publishStocksRollback(StockRollbackEvent event) {
        kafkaTemplate.send(KafkaTopicsConfig.STOCKS_ROLLBACK_TOPIC, event.orderId().toString(), event);
    }
}
