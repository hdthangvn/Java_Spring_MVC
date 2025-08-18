package vn.hoidanit.laptopshop.repository;

import vn.hoidanit.laptopshop.domain.User; // Thêm import này
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User eric);

    // Custom query methods (if needed) can be defined here
    List<User> findByEmailAndAddress(String email, String address);

    List<User> findByEmail(String email);

    List<User> findAll();
}
