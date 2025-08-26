package az.rahibjafar.mscustomer.event.consumer;

import az.rahibjafar.mscustomer.config.KafkaTopicsConfig;
import az.rahibjafar.mscustomer.event.model.PaymentCreatedEvent;
import az.rahibjafar.mscustomer.exception.InsufficientAmountException;
import az.rahibjafar.mscustomer.service.AccountService;
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
public class PaymentCreatedListener {

    private static final Logger log = Logger.getLogger(PaymentCreatedListener.class.getName());
    private final AccountService accountService;

    public PaymentCreatedListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "true",
            exclude = { InsufficientAmountException.class },
            traversingCauses = "true",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            id = "${kafka.ids.created}",
            topics = KafkaTopicsConfig.PAYMENT_CREATED_TOPIC,
            groupId = "${kafka.groups.created}",
            containerFactory = "paymentCreatedEventFactory",
            properties = {
                    "spring.json.value.default.type=az.rahibjafar.mscustomer.event.model.PaymentCreatedEvent",
                    "spring.json.use.type.headers=false"
            }
    )
    public void onPaymentCreated(
            @Payload PaymentCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            ConsumerRecord<String, PaymentCreatedEvent> record,
            Acknowledgment ack
    ) {
        try {
            log.info(String.format("Got stocks-reserved. key=%s, partition=%d, offset=%d, payload=%s",
                    key, record.partition(), record.offset(), event));

            accountService.Transfer(event);

            ack.acknowledge();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}