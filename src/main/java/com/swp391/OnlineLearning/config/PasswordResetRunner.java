package com.swp391.OnlineLearning.config;

import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordResetRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String rawPassword = "thehieu03";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Verify encode works
        boolean selfCheck = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("====== PASSWORD RESET RUNNER ======");
        System.out.println("Encoded password: " + encodedPassword);
        System.out.println("Self-check (should be true): " + selfCheck);
        
        List<User> users = userRepository.findAll();
        System.out.println("Found " + users.size() + " users to reset");
        
        for (User user : users) {
            user.setPassword(encodedPassword);
        }
        userRepository.saveAll(users);
        userRepository.flush();
        
        // Verify after save
        List<User> savedUsers = userRepository.findAll();
        for (User u : savedUsers) {
            boolean match = passwordEncoder.matches(rawPassword, u.getPassword());
            System.out.println("User " + u.getEmail() + " | password match: " + match + " | stored: " + u.getPassword().substring(0, 20) + "...");
        }
        
        System.out.println("====== RESET COMPLETE ======");
    }
}
