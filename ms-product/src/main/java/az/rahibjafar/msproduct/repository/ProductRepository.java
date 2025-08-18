package az.rahibjafar.msproduct.repository;

import az.rahibjafar.msproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
