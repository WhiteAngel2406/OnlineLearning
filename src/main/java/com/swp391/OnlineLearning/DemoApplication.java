package com.swp391.OnlineLearning;

import com.swp391.OnlineLearning.Config.StorageProperties;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.UserRole;
import com.swp391.OnlineLearning.repository.RoleRepository;
import com.swp391.OnlineLearning.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(RoleRepository roleRepository,
								   UserRepository userRepository,
								   PasswordEncoder passwordEncoder) {
		return args -> {
			// Create roles if not exist
			UserRole adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
				UserRole role = new UserRole("ADMIN", "Administrator role");
				return roleRepository.save(role);
			});
			UserRole expertRole = roleRepository.findByName("EXPERT").orElseGet(() -> {
				UserRole role = new UserRole("EXPERT", "Expert role");
				return roleRepository.save(role);
			});
			UserRole marketingRole = roleRepository.findByName("MARKETING").orElseGet(() -> {
				UserRole role = new UserRole("MARKETING", "Marketing role");
				return roleRepository.save(role);
			});
			UserRole userRole = roleRepository.findByName("USER").orElseGet(() -> {
				UserRole role = new UserRole("USER", "Regular user role");
				return roleRepository.save(role);
			});

			User admin = userRepository.findByEmail("admin@example.com").orElse(new User());
			if (admin.getEmail() == null) {
				admin.setEmail("admin@example.com");
				admin.setFullName("Admin User");
				admin.setEnabled(true);
			}
			if (adminRole != null) admin.setRole(adminRole);
			admin.setPassword(passwordEncoder.encode("123"));

			User expert = userRepository.findByEmail("expert@example.com").orElse(new User());
			if (expert.getEmail() == null) {
				expert.setEmail("expert@example.com");
				expert.setFullName("Expert User");
				expert.setEnabled(true);
			}
			if (expertRole != null) expert.setRole(expertRole);
			expert.setPassword(passwordEncoder.encode("123"));

			User marketing = userRepository.findByEmail("marketing@example.com").orElse(new User());
			if (marketing.getEmail() == null) {
				marketing.setEmail("marketing@example.com");
				marketing.setFullName("Marketing User");
				marketing.setEnabled(true);
			}
			if (marketingRole != null) marketing.setRole(marketingRole);
			marketing.setPassword(passwordEncoder.encode("123"));

			User user = userRepository.findByEmail("user@example.com").orElse(new User());
			if (user.getEmail() == null) {
				user.setEmail("user@example.com");
				user.setFullName("Regular User");
				user.setEnabled(true);
			}
			if (userRole != null) user.setRole(userRole);
			user.setPassword(passwordEncoder.encode("123"));

			userRepository.saveAll(List.of(admin, expert, marketing, user));
			System.out.println(">>> Đã tạo dữ liệu người dùng mẫu!");
		};
	}
}
