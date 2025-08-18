package az.rahibjafar.msproduct.repository;

import az.rahibjafar.msproduct.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CategoryRepository  extends JpaRepository<Category, UUID> {
}
