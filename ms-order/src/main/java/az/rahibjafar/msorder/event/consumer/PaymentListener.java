package az.rahibjafar.msorder.event.consumer;

import az.rahibjafar.msorder.config.KafkaTopicsConfig;
import az.rahibjafar.msorder.event.model.PaymentCancelledEvent;
import az.rahibjafar.msorder.event.model.PaymentCompletedEvent;
import az.rahibjafar.msorder.model.OrderStatus;
import az.rahibjafar.msorder.service.OrderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;


@Component
public class PaymentListener {

    private static final Logger log = Logger.getLogger(PaymentListener.class.getName());
    private final OrderService orderService;

    public PaymentListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "true",
            include = { RuntimeException.class },
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            id = "${kafka.ids.completed}",
            topics = KafkaTopicsConfig.PAYMENT_COMPLETED_TOPIC,
            groupId = "${kafka.groups.completed}",
            containerFactory = "paymentCompletedEventFactory",
            properties = {
                    "spring.json.value.default.type=az.rahibjafar.msorder.event.model.PaymentCompletedEvent",
                    "spring.json.use.type.headers=false"
            }
    )
    public void onPaymentCompleted(
            @Payload PaymentCompletedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            ConsumerRecord<String, PaymentCompletedEvent> record,
            Acknowledgment ack
    ) {
        try {
            log.info(String.format("Got stocks-reserved. key=%s, partition=%d, offset=%d, payload=%s",
                    key, record.partition(), record.offset(), event));

            orderService.updateStatus(OrderStatus.CONFIRMED, event.orderId());

            ack.acknowledge();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "true",
            include = { RuntimeException.class },
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            id = "${kafka.ids.cancelledPayment}",
            topics = KafkaTopicsConfig.PAYMENT_CANCELLED_TOPIC,
            groupId = "${kafka.groups.cancelledPayment}",
            containerFactory = "paymentCancelledEventFactory",
            properties = {
                    "spring.json.value.default.type=az.rahibjafar.msorder.event.model.PaymentCancelledEvent",
                    "spring.json.use.type.headers=false"
            }
    )
    public void onPaymentCancelled(
            @Payload PaymentCancelledEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            ConsumerRecord<String, PaymentCancelledEvent> record,
            Acknowledgment ack
    ) {
        try {
            log.info(String.format("Got stocks-reserved. key=%s, partition=%d, offset=%d, payload=%s",
                    key, record.partition(), record.offset(), event));

            orderService.updateStatus(OrderStatus.CANCELLED, event.orderId());

            ack.acknowledge();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}