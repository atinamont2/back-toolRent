package group3.tool.rent.category.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import group3.tool.rent.category.model.Category;
public interface CategoryRepository extends JpaRepository<Category, Long> {
}