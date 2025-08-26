package az.rahibjafar.mspayment.event.consumer;

import az.rahibjafar.mspayment.config.KafkaTopicsConfig;
import az.rahibjafar.mspayment.dto.CreatePaymentRequest;
import az.rahibjafar.mspayment.event.model.StockReservedEvent;
import az.rahibjafar.mspayment.service.PaymentService;
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
public class StockListener {

    private static final Logger log = Logger.getLogger(StockListener.class.getName());
    private final PaymentService paymentService;
    private static final String glAccountNumber = "9999999999";

    public StockListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "true",
            include = { RuntimeException.class },
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            id = "${kafka.ids.reserved}",
            topics = KafkaTopicsConfig.STOCKS_RESERVED_TOPIC,
            groupId = "${kafka.groups.reserved}",
            containerFactory = "stockReservedEventFactory",
            properties = {
                    "spring.json.value.default.type=az.rahibjafar.mspayment.event.model.StockReservedEvent",
                    "spring.json.use.type.headers=false"
            }
    )
    public void onStockReserved(
            @Payload StockReservedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            ConsumerRecord<String, StockReservedEvent> record,
            Acknowledgment ack
    ) {
        try {
            log.info(String.format("Got stocks-reserved. key=%s, partition=%d, offset=%d, payload=%s",
                    key, record.partition(), record.offset(), event));

            paymentService.create(
                    new CreatePaymentRequest(
                            event.orderId(), event.accountNumber(),
                            glAccountNumber, event.totalAmount()
                    )
            );

            ack.acknowledge();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}