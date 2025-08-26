package az.rahibjafar.msorder.event.consumer;

import az.rahibjafar.msorder.event.model.OrderCancelledEvent;
import az.rahibjafar.msorder.event.model.StockReservedEvent;
import az.rahibjafar.msorder.model.OrderStatus;
import az.rahibjafar.msorder.service.OrderService;
import az.rahibjafar.msorder.config.KafkaTopicsConfig;
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
    private final OrderService orderService;

    public StockListener(OrderService orderService) {
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
            id = "${kafka.ids.reserved}",
            topics = KafkaTopicsConfig.STOCKS_RESERVED_TOPIC,
            groupId = "${kafka.groups.reserved}",
            containerFactory = "stockReservedEventFactory",
            properties = {
                    "spring.json.value.default.type=az.rahibjafar.msorder.event.model.StockReservedEvent",
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

            orderService.updateStatus(OrderStatus.RESERVED, event.orderId());
            orderService.updateTotalAmount(event.totalAmount(), event.orderId());

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
            id = "${kafka.ids.cancelled}",
            topics = KafkaTopicsConfig.ORDERS_CANCELLED_TOPIC,
            groupId = "${kafka.groups.cancelled}",
            containerFactory = "orderCancelledEventFactory",
            properties = {
                    "spring.json.value.default.type=az.rahibjafar.msorder.event.model.OrderCancelledEvent",
                    "spring.json.use.type.headers=false"
            }
    )
    public void onOrderCancelled(
            @Payload OrderCancelledEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            ConsumerRecord<String, OrderCancelledEvent> record,
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