package az.rahibjafar.mspayment.event.producer;

import az.rahibjafar.mspayment.config.KafkaTopicsConfig;
import az.rahibjafar.mspayment.event.model.PaymentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class PaymentEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCreated(PaymentCreatedEvent event) {
        kafkaTemplate.send(KafkaTopicsConfig.PAYMENT_CREATED_TOPIC, event.orderId().toString(), event);
    }
}
