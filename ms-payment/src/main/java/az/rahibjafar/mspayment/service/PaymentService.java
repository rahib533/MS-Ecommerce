package az.rahibjafar.mspayment.service;

import az.rahibjafar.mspayment.dto.CreatePaymentRequest;
import az.rahibjafar.mspayment.dto.PaymentDto;
import az.rahibjafar.mspayment.dto.converter.PaymentDtoConverter;
import az.rahibjafar.mspayment.exception.InvalidPaymentStatusType;
import az.rahibjafar.mspayment.exception.PaymentNotFoundException;
import az.rahibjafar.mspayment.model.Payment;
import az.rahibjafar.mspayment.model.PaymentStatus;
import az.rahibjafar.mspayment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentDtoConverter paymentDtoConverter;

    public PaymentService(PaymentRepository paymentRepository, PaymentDtoConverter paymentDtoConverter) {
        this.paymentRepository = paymentRepository;
        this.paymentDtoConverter = paymentDtoConverter;
    }

    public List<PaymentDto> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentDtoConverter::convertToOrderDto)
                .collect(Collectors.toList());
    }

    public PaymentDto getById(UUID id) {
        return paymentDtoConverter.convertToOrderDto(paymentRepository.findById(id).orElseThrow(
                () -> new PaymentNotFoundException("Payment with id " + id + " not found")
        ));
    }

    public Payment findById(UUID id) {
        return paymentRepository.findById(id).orElseThrow(
                () -> new PaymentNotFoundException("Payment with id " + id + " not found")
        );
    }

    public PaymentDto updateStatus(PaymentStatus paymentStatus, UUID id) {
        Payment payment = findById(id);
        switch (paymentStatus) {
            case COMPLETED ->{
                payment.setStatus(PaymentStatus.COMPLETED);
                payment.setStatusCompletedDate(LocalDateTime.now());
            }
            case FAILED ->{
                payment.setStatus(PaymentStatus.FAILED);
                payment.setStatusFailedDate(LocalDateTime.now());
            }
            default -> throw new InvalidPaymentStatusType("Invalid payment status");
        }
        return paymentDtoConverter.convertToOrderDto(paymentRepository.save(payment));
    }

    public PaymentDto create(final CreatePaymentRequest createPaymentRequest) {
        Payment payment = new Payment(
                createPaymentRequest.getOrderId(),
                createPaymentRequest.getFromAccountNumber(),
                createPaymentRequest.getToAccountNumber(),
                createPaymentRequest.getTotalAmount()
        );

        return paymentDtoConverter.convertToOrderDto(paymentRepository.save(payment));
    }
}
