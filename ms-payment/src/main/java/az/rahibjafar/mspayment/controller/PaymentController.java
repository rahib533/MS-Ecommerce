package az.rahibjafar.mspayment.controller;

import az.rahibjafar.mspayment.dto.CreatePaymentRequest;
import az.rahibjafar.mspayment.dto.PaymentDto;
import az.rahibjafar.mspayment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getAll")
    public List<PaymentDto> getAll() {
        return paymentService.findAll();
    }

    @PostMapping("/create")
    public PaymentDto create(@RequestBody CreatePaymentRequest createPaymentRequest) {
        return paymentService.create(createPaymentRequest);
    }
}
