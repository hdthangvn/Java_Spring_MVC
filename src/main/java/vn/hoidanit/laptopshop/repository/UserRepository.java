package vn.hoidanit.laptopshop.repository;

import vn.hoidanit.laptopshop.domain.User; // Thêm import này
import org.springframework.data.repository.CrudRepository; // ← THÊM import này
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User hoidanit);
    // Custom query methods (if needed) can be defined here
}
