package az.rahibjafar.mscustomer.repository;

import az.rahibjafar.mscustomer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
