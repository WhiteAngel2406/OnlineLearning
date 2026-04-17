package com.swp391.OnlineLearning.config;

import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class PasswordResetConfig {

    @Bean
    public CommandLineRunner resetPasswords(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("========== BẮT ĐẦU RESET MẬT KHẨU ==========");
            List<User> users = userRepository.findAll();
            boolean updated = false;
            
            // Mã hóa mật khẩu "password123" bằng BCryptPasswordEncoder đã được cấu hình trong project
            String newEncodedPassword = passwordEncoder.encode("password123");
            
            for (User user : users) {
                user.setPassword(newEncodedPassword);
                updated = true;
            }
            
            if (updated) {
                userRepository.saveAll(users);
                System.out.println("Thành công: Đã đổi mật khẩu của toàn bộ tài khoản (" + users.size() + " accounts) thành 'password123'.");
            } else {
                System.out.println("Không tìm thấy người dùng nào trong database.");
            }
            System.out.println("========== KẾT THÚC RESET MẬT KHẨU ==========");
        };
    }
}
