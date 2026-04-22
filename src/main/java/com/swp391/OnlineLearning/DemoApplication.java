package com.swp391.OnlineLearning;

<<<<<<< HEAD
import com.swp391.OnlineLearning.Config.StorageProperties;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.UserRole;
import com.swp391.OnlineLearning.Repository.RoleRepository;
import com.swp391.OnlineLearning.Repository.UserRepository;
=======
import com.swp391.OnlineLearning.config.StorageProperties;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.model.UserRole;
import com.swp391.OnlineLearning.repository.RoleRepository;
import com.swp391.OnlineLearning.repository.UserRepository;
>>>>>>> main
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
								   com.swp391.OnlineLearning.repository.CourseCategoryRepository categoryRepository,
								   com.swp391.OnlineLearning.repository.CourseRepository courseRepository,
								   com.swp391.OnlineLearning.repository.BlogCategoryRepository blogCategoryRepository,
								   com.swp391.OnlineLearning.repository.BlogRepository blogRepository,
								   com.swp391.OnlineLearning.repository.SliderRepository sliderRepository,
								   PasswordEncoder passwordEncoder) {
		return args -> {
<<<<<<< HEAD
			// Chỉ tạo dữ liệu nếu chưa có

			User admin = userRepository.findByEmail("admin@example.com").orElse(new User());
			admin.setPassword(passwordEncoder.encode("123"));
			User expert = userRepository.findByEmail("expert@example.com").orElse(new User());
			expert.setPassword(passwordEncoder.encode("123"));
			User marketing = userRepository.findByEmail("marketing@example.com").orElse(new User());
			marketing.setPassword(passwordEncoder.encode("123"));
			User user = userRepository.findByEmail("user@example.com").orElse(new User());
			user.setPassword(passwordEncoder.encode("123"));
			userRepository.saveAll(List.of(admin, expert, user, marketing));
			System.out.println(">>> Đã tạo dữ liệu người dùng mẫu!");
=======
			// Create roles if not exist
			UserRole adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> roleRepository.save(new UserRole("ADMIN", "Administrator role")));
			UserRole expertRole = roleRepository.findByName("EXPERT").orElseGet(() -> roleRepository.save(new UserRole("EXPERT", "Expert role")));
			UserRole marketingRole = roleRepository.findByName("MARKETING").orElseGet(() -> roleRepository.save(new UserRole("MARKETING", "Marketing role")));
			UserRole userRole = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new UserRole("USER", "Regular user role")));

			User admin = userRepository.findByEmail("admin@example.com").orElse(new User());
			if (admin.getEmail() == null) { admin.setEmail("admin@example.com"); admin.setFullName("Admin User"); admin.setEnabled(true); }
			admin.setRole(adminRole); admin.setPassword(passwordEncoder.encode("123"));

			User expert = userRepository.findByEmail("expert@example.com").orElse(new User());
			if (expert.getEmail() == null) { expert.setEmail("expert@example.com"); expert.setFullName("Expert User"); expert.setEnabled(true); }
			expert.setRole(expertRole); expert.setPassword(passwordEncoder.encode("123"));

			User marketing = userRepository.findByEmail("marketing@example.com").orElse(new User());
			if (marketing.getEmail() == null) { marketing.setEmail("marketing@example.com"); marketing.setFullName("Marketing User"); marketing.setEnabled(true); }
			marketing.setRole(marketingRole); marketing.setPassword(passwordEncoder.encode("123"));

			User user = userRepository.findByEmail("user@example.com").orElse(new User());
			if (user.getEmail() == null) { user.setEmail("user@example.com"); user.setFullName("Regular User"); user.setEnabled(true); }
			user.setRole(userRole); user.setPassword(passwordEncoder.encode("123"));

			userRepository.saveAll(List.of(admin, expert, marketing, user));

			String imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfFXRVrxEzWOUU5Z7XaFiRaV3kUGiSpQ7JMA&s";

			// Seed Course Categories
			if (categoryRepository.count() == 0) {
				com.swp391.OnlineLearning.model.CourseCategory c1 = new com.swp391.OnlineLearning.model.CourseCategory("Tiếng Anh Giao Tiếp", "Các khóa học cải thiện kỹ năng giao tiếp tiếng Anh cơ bản và nâng cao", true);
				com.swp391.OnlineLearning.model.CourseCategory c2 = new com.swp391.OnlineLearning.model.CourseCategory("Luyện thi IELTS", "Chinh phục IELTS với các chiến lược làm bài hiệu quả", true);
				com.swp391.OnlineLearning.model.CourseCategory c3 = new com.swp391.OnlineLearning.model.CourseCategory("Từ Vựng & Ngữ Pháp", "Củng cố nền tảng ngữ pháp và mở rộng vốn từ vựng tiếng Anh", true);
				categoryRepository.saveAll(List.of(c1, c2, c3));

				// Seed Courses
				com.swp391.OnlineLearning.model.Course course1 = new com.swp391.OnlineLearning.model.Course("Tiếng Anh Giao Tiếp Cho Người Đi Làm", "Giao tiếp tự tin nơi công sở", "Khóa học tập trung vào các tình huống giao tiếp tiếng Anh thực tế trong môi trường văn phòng, cách viết email và ứng xử chuyên nghiệp.", "Không yêu cầu", imgUrl, 1200000.0, 10.0, true, com.swp391.OnlineLearning.model.Course.CourseStatus.PUBLISHED, 0, c1);
				course1.setAuthor(expert);
				com.swp391.OnlineLearning.model.Course course2 = new com.swp391.OnlineLearning.model.Course("Luyện thi IELTS 6.5+ Cấp Tốc", "Chinh phục IELTS trong 3 tháng", "Cung cấp lộ trình luyện thi IELTS cường độ cao, bám sát các dạng đề thi thật với phương pháp giải chi tiết.", "Trình độ trung bình (B1)", imgUrl, 2500000.0, 20.0, true, com.swp391.OnlineLearning.model.Course.CourseStatus.PUBLISHED, 0, c2);
				course2.setAuthor(expert);
				com.swp391.OnlineLearning.model.Course course3 = new com.swp391.OnlineLearning.model.Course("Ngữ Pháp Tiếng Anh Toàn Diện", "Nắm trắc cấu trúc ngữ pháp", "Khóa học giúp củng cố toàn bộ các điểm ngữ pháp từ cơ bản đến nâng cao dùng trong văn viết và nói.", "Không yêu cầu", imgUrl, 800000.0, 0.0, true, com.swp391.OnlineLearning.model.Course.CourseStatus.PUBLISHED, 0, c3);
				course3.setAuthor(expert);
				courseRepository.saveAll(List.of(course1, course2, course3));
			}

			// Seed Blog Categories
			if (blogCategoryRepository.count() == 0) {
				com.swp391.OnlineLearning.model.BlogCategory b1 = new com.swp391.OnlineLearning.model.BlogCategory("Kinh nghiệm ôn ngoại ngữ", "kinh-nghiem-on-ngoai-ngu");
				com.swp391.OnlineLearning.model.BlogCategory b2 = new com.swp391.OnlineLearning.model.BlogCategory("Mẹo làm bài thi", "meo-lam-bai-thi");
				blogCategoryRepository.saveAll(List.of(b1, b2));

				// Seed Blogs
				com.swp391.OnlineLearning.model.Blog blog1 = new com.swp391.OnlineLearning.model.Blog(null, "Cách Tự Học IELTS Writing Lên 7.0 Ở Nhà", imgUrl, "Kinh nghiệm rèn luyện kỹ năng viết IELTS tại nhà mà không cần đến trung tâm.", "Trong bài viết này, chúng ta sẽ cùng đi sâu vào phương pháp paraphrase và cách lên dàn ý nhanh để ghi trọn điểm IELTS Writing...", b1, marketing);
				blog1.setStatus(com.swp391.OnlineLearning.model.Blog.BlogStatus.PUBLISHED);
				com.swp391.OnlineLearning.model.Blog blog2 = new com.swp391.OnlineLearning.model.Blog(null, "5 Bước Để Nói Tiếng Anh Trôi Chảy Tự Nhiên", imgUrl, "Bí kíp giúp bạn nâng cao phản xạ khi trò chuyện với người nước ngoài.", "Đừng quá tập trung vào ngữ pháp khi nói, hãy bắt chước cách phát âm (shadowing) để rèn luyện thói quen phản xạ tự nhiên của não bộ...", b1, marketing);
				blog2.setStatus(com.swp391.OnlineLearning.model.Blog.BlogStatus.PUBLISHED);
				com.swp391.OnlineLearning.model.Blog blog3 = new com.swp391.OnlineLearning.model.Blog(null, "Các Lỗi Ngữ Pháp Thường Gặp Trong Bài Thi TOEIC", imgUrl, "Phân loại các bẫy ngữ pháp phổ biến và cách tránh mất điểm oan.", "Phần thi Reading của TOEIC luôn ẩn chứa vô số rủi ro với các mẫu câu đảo ngữ và thì hoàn thành. Hãy cùng điểm qua...", b2, marketing);
				blog3.setStatus(com.swp391.OnlineLearning.model.Blog.BlogStatus.PUBLISHED);
				blogRepository.saveAll(List.of(blog1, blog2, blog3));
			}

			// Seed Sliders
			if (sliderRepository.count() == 0) {
				com.swp391.OnlineLearning.model.Slider s1 = new com.swp391.OnlineLearning.model.Slider();
				s1.setTitle("Hệ Thống Tiếng Anh Trực Tuyến Hàng Đầu");
				s1.setDescription("Mở rộng tiềm năng ngoại ngữ của bạn với các chuyên gia uy tín.");
				s1.setImageUrl(imgUrl);
				s1.setLinkUrl("/courses");
				s1.setStatus("ACTIVE");
				s1.setOrderNumber(1);
				s1.setUser(marketing);
				
				com.swp391.OnlineLearning.model.Slider s2 = new com.swp391.OnlineLearning.model.Slider();
				s2.setTitle("Ưu Đãi Đặc Biệt Khóa Luyện Thi IELTS Cấp Tốc");
				s2.setDescription("Cam kết đầu ra chuẩn 6.5+ chỉ sau 3 tháng. Đăng ký ngay để nhận ưu đãi lên đến 20%!");
				s2.setImageUrl(imgUrl);
				s2.setLinkUrl("/promotions");
				s2.setStatus("ACTIVE");
				s2.setOrderNumber(2);
				s2.setUser(marketing);
				
				sliderRepository.saveAll(List.of(s1, s2));
			}

			System.out.println(">>> Đã tạo dữ liệu mẫu thành công với đầy đủ Courses, Blogs, Sliders!");
>>>>>>> main
		};
	}
}
