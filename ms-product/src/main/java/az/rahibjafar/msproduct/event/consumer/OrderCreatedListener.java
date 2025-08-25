package az.rahibjafar.msproduct.event.consumer;

import az.rahibjafar.msproduct.config.KafkaTopicsConfig;
import az.rahibjafar.msproduct.dto.ProductDto;
import az.rahibjafar.msproduct.event.model.OrderCancelledEvent;
import az.rahibjafar.msproduct.event.model.OrderCreatedEvent;
import az.rahibjafar.msproduct.event.model.StockReservedEvent;
import az.rahibjafar.msproduct.event.producer.StockEventProducer;
import az.rahibjafar.msproduct.exception.ProductNotFoundException;
import az.rahibjafar.msproduct.exception.ProductNotInStock;
import az.rahibjafar.msproduct.service.ProductService;
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
import java.math.BigDecimal;
import java.util.logging.Logger;


@Component
public class OrderCreatedListener {

    private static final Logger log = Logger.getLogger(OrderCreatedListener.class.getName());
    private final ProductService productService;
    private final StockEventProducer stockEventProducer;

    public OrderCreatedListener(ProductService productService, StockEventProducer stockEventProducer) {
        this.productService = productService;
        this.stockEventProducer = stockEventProducer;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "true",
            exclude = {
                    ProductNotFoundException.class,
                    ProductNotInStock.class
            },
            traversingCauses = "true",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            topics = KafkaTopicsConfig.ORDERS_CREATED_TOPIC,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onMessage(
            @Payload OrderCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            ConsumerRecord<String, OrderCreatedEvent> record,
            Acknowledgment ack
    ) {
        try {
            log.info(String.format("Got order-created. key=%s, partition=%d, offset=%d, payload=%s",
                    key, record.partition(), record.offset(), event));

            ProductDto product = productService.getById(event.productId());
            if (product == null) {
                stockEventProducer.publishOrderCancelled(new OrderCancelledEvent(event.orderId(),
                        "Product not found for id " + event.productId()));
                ack.acknowledge();
                throw new ProductNotFoundException("Product not found for id " + event.productId());
            }
            if (product.getStockCount() < event.count()){
                stockEventProducer.publishOrderCancelled(new OrderCancelledEvent(event.orderId(),
                        "Product not in stock for id " + event.productId() + " count: " + event.count()));
                ack.acknowledge();
                throw new ProductNotInStock("Product not in stock for id " + event.productId() + " count: " + event.count());
            }

            productService.updateStock(product.getId(), (product.getStockCount() - event.count()));

            BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(event.count()));

            StockReservedEvent stockReservedEvent = new StockReservedEvent(event.orderId(), event.productId(),
                    event.customerId(), event.accountNumber(), event.count(), totalAmount);

            stockEventProducer.publishStockReserved(stockReservedEvent);

            ack.acknowledge();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}