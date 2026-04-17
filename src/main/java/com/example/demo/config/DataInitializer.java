package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.model.enums.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CourseCategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create roles if not exist
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            UserRole adminRole = new UserRole();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Administrator");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("EXPERT").isEmpty()) {
            UserRole expertRole = new UserRole();
            expertRole.setName("EXPERT");
            expertRole.setDescription("Expert");
            roleRepository.save(expertRole);
        }

        if (roleRepository.findByName("LEARNER").isEmpty()) {
            UserRole learnerRole = new UserRole();
            learnerRole.setName("LEARNER");
            learnerRole.setDescription("Learner");
            roleRepository.save(learnerRole);
        }

        // Create admin user if not exist
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Quản trị viên");
            admin.setGender(User.Gender.MALE);
            admin.setEnabled(true);
            admin.setRole(roleRepository.findByName("ADMIN").get());
            userRepository.save(admin);
            System.out.println("Admin user created: admin@gmail.com / admin123");
        }

        // Create expert user if not exist
        if (userRepository.findByEmail("expert@gmail.com").isEmpty()) {
            User expert = new User();
            expert.setEmail("expert@gmail.com");
            expert.setPassword(passwordEncoder.encode("expert123"));
            expert.setFullName("Giảng viên Nguyễn Văn A");
            expert.setGender(User.Gender.MALE);
            expert.setEnabled(true);
            expert.setRole(roleRepository.findByName("EXPERT").get());
            userRepository.save(expert);
        }

        // Create learner user if not exist
        if (userRepository.findByEmail("learner@gmail.com").isEmpty()) {
            User learner = new User();
            learner.setEmail("learner@gmail.com");
            learner.setPassword(passwordEncoder.encode("learner123"));
            learner.setFullName("Người học Trần Thị B");
            learner.setGender(User.Gender.FEMALE);
            learner.setEnabled(true);
            learner.setRole(roleRepository.findByName("LEARNER").get());
            userRepository.save(learner);
        }

        // Create disabled user if not exist
        if (userRepository.findByEmail("disabled@gmail.com").isEmpty()) {
            User disabledUser = new User();
            disabledUser.setEmail("disabled@gmail.com");
            disabledUser.setPassword(passwordEncoder.encode("disabled123"));
            disabledUser.setFullName("Tài khoản bị vô hiệu");
            disabledUser.setGender(User.Gender.OTHER);
            disabledUser.setEnabled(false);
            disabledUser.setRole(roleRepository.findByName("LEARNER").get());
            userRepository.save(disabledUser);
        }

        // Create categories if empty
        if (categoryRepository.count() == 0) {
            CourseCategory cat1 = new CourseCategory();
            cat1.setName("Tiếng Anh");
            cat1.setDescription("Các khóa học Tiếng Anh tổng quát");
            cat1.setActive(true);
            categoryRepository.save(cat1);

            CourseCategory cat2 = new CourseCategory();
            cat2.setName("IELTS");
            cat2.setDescription("Khóa học luyện thi IELTS");
            cat2.setActive(true);
            categoryRepository.save(cat2);

            CourseCategory cat3 = new CourseCategory();
            cat3.setName("Giao tiếp");
            cat3.setDescription("Khóa học giao tiếp tiếng Anh");
            cat3.setActive(true);
            categoryRepository.save(cat3);

            CourseCategory cat4 = new CourseCategory();
            cat4.setName("Business English");
            cat4.setDescription("Tiếng Anh thương mại");
            cat4.setActive(false);
            categoryRepository.save(cat4);
        }

        // Create courses if empty
        if (courseRepository.count() == 0) {
            User expert = userRepository.findByEmail("expert@gmail.com").orElse(null);
            if (expert != null) {
                CourseCategory cat1 = categoryRepository.findAll().get(0);
                CourseCategory cat2 = categoryRepository.findAll().get(1);

                Course course1 = new Course();
                course1.setName("Anh Văn Cơ Bản");
                course1.setDescription("Khóa học Anh Văn cơ bản dành cho người mới bắt đầu");
                course1.setPrice(new BigDecimal("990000"));
                course1.setCategory(cat1);
                course1.setExpert(expert);
                course1.setStatus(CourseStatus.APPROVED);
                course1.setFeatured(true);
                courseRepository.save(course1);

                Course course2 = new Course();
                course2.setName("IELTS 7.0 Preparation");
                course2.setDescription("Khóa học luyện thi IELTS đạt 7.0 điểm");
                course2.setPrice(new BigDecimal("2500000"));
                course2.setCategory(cat2);
                course2.setExpert(expert);
                course2.setStatus(CourseStatus.APPROVED);
                course2.setFeatured(true);
                courseRepository.save(course2);

                Course course3 = new Course();
                course3.setName("Giao tiếp Anh Ngữ");
                course3.setDescription("Cải thiện kỹ năng giao tiếp tiếng Anh");
                course3.setPrice(new BigDecimal("1500000"));
                course3.setCategory(cat1);
                course3.setExpert(expert);
                course3.setStatus(CourseStatus.PENDING);
                course3.setFeatured(false);
                courseRepository.save(course3);

                Course course4 = new Course();
                course4.setName("Business English");
                course4.setDescription("Tiếng Anh thương mại cho doanh nhân");
                course4.setPrice(new BigDecimal("3500000"));
                course4.setCategory(cat1);
                course4.setExpert(expert);
                course4.setStatus(CourseStatus.REJECTED);
                course4.setFeatured(false);
                courseRepository.save(course4);
            }
        }

        // Create orders if empty
        if (orderRepository.count() == 0) {
            User learner = userRepository.findByEmail("learner@gmail.com").orElse(null);
            User disabledUser = userRepository.findByEmail("disabled@gmail.com").orElse(null);
            if (learner != null && !courseRepository.findAll().isEmpty()) {
                Course course1 = courseRepository.findAll().get(0);
                Course course2 = courseRepository.findAll().get(1);

                Order order1 = new Order();
                order1.setUser(learner);
                order1.setCourse(course1);
                order1.setAmount(course1.getPrice().doubleValue());
                order1.setStatus(OrderStatus.SUCCESS);
                order1.setOrderCode("ORD-001");
                order1.setOrderDate(LocalDateTime.now().minusDays(5));
                order1.setVnpTransactionNo("VNPAY123456");
                orderRepository.save(order1);

                Order order2 = new Order();
                order2.setUser(learner);
                order2.setCourse(course2);
                order2.setAmount(course2.getPrice().doubleValue());
                order2.setStatus(OrderStatus.PENDING);
                order2.setOrderCode("ORD-002");
                order2.setOrderDate(LocalDateTime.now().minusDays(2));
                orderRepository.save(order2);

                if (disabledUser != null) {
                    Order order3 = new Order();
                    order3.setUser(disabledUser);
                    order3.setCourse(course1);
                    order3.setAmount(course1.getPrice().doubleValue());
                    order3.setStatus(OrderStatus.FAILED);
                    order3.setOrderCode("ORD-003");
                    order3.setOrderDate(LocalDateTime.now().minusDays(1));
                    orderRepository.save(order3);
                }
            }
        }

        System.out.println("=== Sample data initialized ===");
    }
}
