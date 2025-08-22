package az.rahibjafar.msproduct.event.producer;

import az.rahibjafar.msproduct.config.KafkaTopicsConfig;
import az.rahibjafar.msproduct.event.model.OrderCancelledEvent;
import az.rahibjafar.msproduct.event.model.StockReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class StockEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public StockEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStockReserved(StockReservedEvent event) {
        kafkaTemplate.send(KafkaTopicsConfig.STOCKS_RESERVED_TOPIC, event.orderId().toString(), event);
    }

    public void publishOrderCancelled(OrderCancelledEvent event) {
        kafkaTemplate.send(KafkaTopicsConfig.ORDERS_CANCELLED_TOPIC, event.orderId().toString(), event);
    }
}
