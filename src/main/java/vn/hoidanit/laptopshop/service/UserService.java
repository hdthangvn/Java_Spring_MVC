package vn.hoidanit.laptopshop.service;

// 
import vn.hoidanit.laptopshop.domain.User; // Sửa lại import này

import vn.hoidanit.laptopshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleSaveUser(User user) {
        User eric = this.userRepository.save(user);
        System.out.println(eric);
        return eric;
        // Handle user saving logic here
    }
}
