package az.rahibjafar.mscustomer.repository;

import az.rahibjafar.mscustomer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
