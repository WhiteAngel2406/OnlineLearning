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
            System.out.println("========== Báº®T Äáº¦U RESET Máº¬T KHáº¨U ==========");
            List<User> users = userRepository.findAll();
            boolean updated = false;
            
            // MÃ£ hÃ³a máº­t kháº©u "password123" báº±ng BCryptPasswordEncoder Ä‘Ã£ Ä‘Æ°á»£c cáº¥u hÃ¬nh trong project
            String newEncodedPassword = passwordEncoder.encode("password123");
            
            for (User user : users) {
                user.setPassword(newEncodedPassword);
                updated = true;
            }
            
            if (updated) {
                userRepository.saveAll(users);
                System.out.println("ThÃ nh cÃ´ng: ÄÃ£ Ä‘á»•i máº­t kháº©u cá»§a toÃ n bá»™ tÃ i khoáº£n (" + users.size() + " accounts) thÃ nh 'password123'.");
            } else {
                System.out.println("KhÃ´ng tÃ¬m tháº¥y ngÆ°á»i dÃ¹ng nÃ o trong database.");
            }
            System.out.println("========== Káº¾T THÃšC RESET Máº¬T KHáº¨U ==========");
        };
    }
}
