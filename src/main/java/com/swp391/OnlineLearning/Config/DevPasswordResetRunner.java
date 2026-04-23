package com.swp391.OnlineLearning.Config;

import com.swp391.OnlineLearning.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Khi ứng dụng khởi động, component này sẽ reset lại mật khẩu
 * của TẤT CẢ users về "password123" (đã được hash BCrypt).
 *
 * CHỈ DÀNH CHO MÔI TRƯỜNG DEVELOPMENT. Cần xóa khi deploy Production!
 */
@Component
public class DevPasswordResetRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DevPasswordResetRunner.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DevPasswordResetRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String defaultPassword = "123";
        String encodedPassword = passwordEncoder.encode(defaultPassword);

        userRepository.findAll().forEach(user -> {
            user.setPassword(encodedPassword);
            userRepository.save(user);
        });

        long count = userRepository.count();
        log.info("✅ [DEV] Đã reset mật khẩu của {} user(s) về '{}'", count, defaultPassword);
    }
}
