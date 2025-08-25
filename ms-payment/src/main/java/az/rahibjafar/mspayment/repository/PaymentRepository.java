package az.rahibjafar.mspayment.repository;

import az.rahibjafar.mspayment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
