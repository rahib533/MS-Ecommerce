package az.rahibjafar.mscustomer.event.producer;

import az.rahibjafar.mscustomer.config.KafkaTopicsConfig;
import az.rahibjafar.mscustomer.event.model.PaymentCancelledEvent;
import az.rahibjafar.mscustomer.event.model.PaymentCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class PaymentEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        kafkaTemplate.send(KafkaTopicsConfig.PAYMENT_COMPLETED_TOPIC, event.orderId().toString(), event);
    }

    public void publishPaymentCancelled(PaymentCancelledEvent event) {
        kafkaTemplate.send(KafkaTopicsConfig.PAYMENT_CANCELLED_TOPIC, event.orderId().toString(), event);
    }
}
