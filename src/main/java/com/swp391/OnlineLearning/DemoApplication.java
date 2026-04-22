package com.swp391.OnlineLearning;

import com.swp391.OnlineLearning.config.StorageProperties;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.model.UserRole;
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
								   com.swp391.OnlineLearning.repository.CourseCategoryRepository categoryRepository,
								   com.swp391.OnlineLearning.repository.CourseRepository courseRepository,
								   com.swp391.OnlineLearning.repository.BlogCategoryRepository blogCategoryRepository,
								   com.swp391.OnlineLearning.repository.BlogRepository blogRepository,
								   com.swp391.OnlineLearning.repository.SliderRepository sliderRepository,
								   PasswordEncoder passwordEncoder) {
		return args -> {

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
				com.swp391.OnlineLearning.model.CourseCategory c1 = new com.swp391.OnlineLearning.model.CourseCategory("Tiáº¿ng Anh Giao Tiáº¿p", "CÃ¡c khÃ³a há»c cáº£i thiá»‡n ká»¹ nÄƒng giao tiáº¿p tiáº¿ng Anh cÆ¡ báº£n vÃ  nÃ¢ng cao", true);
				com.swp391.OnlineLearning.model.CourseCategory c2 = new com.swp391.OnlineLearning.model.CourseCategory("Luyá»‡n thi IELTS", "Chinh phá»¥c IELTS vá»›i cÃ¡c chiáº¿n lÆ°á»£c lÃ m bÃ i hiá»‡u quáº£", true);
				com.swp391.OnlineLearning.model.CourseCategory c3 = new com.swp391.OnlineLearning.model.CourseCategory("Tá»« Vá»±ng & Ngá»¯ PhÃ¡p", "Cá»§ng cá»‘ ná»n táº£ng ngá»¯ phÃ¡p vÃ  má»Ÿ rá»™ng vá»‘n tá»« vá»±ng tiáº¿ng Anh", true);
				categoryRepository.saveAll(List.of(c1, c2, c3));

				// Seed Courses
				com.swp391.OnlineLearning.model.Course course1 = new com.swp391.OnlineLearning.model.Course("Tiáº¿ng Anh Giao Tiáº¿p Cho NgÆ°á»i Äi LÃ m", "Giao tiáº¿p tá»± tin nÆ¡i cÃ´ng sá»Ÿ", "KhÃ³a há»c táº­p trung vÃ o cÃ¡c tÃ¬nh huá»‘ng giao tiáº¿p tiáº¿ng Anh thá»±c táº¿ trong mÃ´i trÆ°á»ng vÄƒn phÃ²ng, cÃ¡ch viáº¿t email vÃ  á»©ng xá»­ chuyÃªn nghiá»‡p.", "KhÃ´ng yÃªu cáº§u", imgUrl, 1200000.0, 10.0, true, com.swp391.OnlineLearning.model.Course.CourseStatus.PUBLISHED, 0, c1);
				course1.setAuthor(expert);
				com.swp391.OnlineLearning.model.Course course2 = new com.swp391.OnlineLearning.model.Course("Luyá»‡n thi IELTS 6.5+ Cáº¥p Tá»‘c", "Chinh phá»¥c IELTS trong 3 thÃ¡ng", "Cung cáº¥p lá»™ trÃ¬nh luyá»‡n thi IELTS cÆ°á»ng Ä‘á»™ cao, bÃ¡m sÃ¡t cÃ¡c dáº¡ng Ä‘á» thi tháº­t vá»›i phÆ°Æ¡ng phÃ¡p giáº£i chi tiáº¿t.", "TrÃ¬nh Ä‘á»™ trung bÃ¬nh (B1)", imgUrl, 2500000.0, 20.0, true, com.swp391.OnlineLearning.model.Course.CourseStatus.PUBLISHED, 0, c2);
				course2.setAuthor(expert);
				com.swp391.OnlineLearning.model.Course course3 = new com.swp391.OnlineLearning.model.Course("Ngá»¯ PhÃ¡p Tiáº¿ng Anh ToÃ n Diá»‡n", "Náº¯m tráº¯c cáº¥u trÃºc ngá»¯ phÃ¡p", "KhÃ³a há»c giÃºp cá»§ng cá»‘ toÃ n bá»™ cÃ¡c Ä‘iá»ƒm ngá»¯ phÃ¡p tá»« cÆ¡ báº£n Ä‘áº¿n nÃ¢ng cao dÃ¹ng trong vÄƒn viáº¿t vÃ  nÃ³i.", "KhÃ´ng yÃªu cáº§u", imgUrl, 800000.0, 0.0, true, com.swp391.OnlineLearning.model.Course.CourseStatus.PUBLISHED, 0, c3);
				course3.setAuthor(expert);
				courseRepository.saveAll(List.of(course1, course2, course3));
			}

			// Seed Blog Categories
			if (blogCategoryRepository.count() == 0) {
				com.swp391.OnlineLearning.model.BlogCategory b1 = new com.swp391.OnlineLearning.model.BlogCategory("Kinh nghiá»‡m Ã´n ngoáº¡i ngá»¯", "kinh-nghiem-on-ngoai-ngu");
				com.swp391.OnlineLearning.model.BlogCategory b2 = new com.swp391.OnlineLearning.model.BlogCategory("Máº¹o lÃ m bÃ i thi", "meo-lam-bai-thi");
				blogCategoryRepository.saveAll(List.of(b1, b2));

				// Seed Blogs
				com.swp391.OnlineLearning.model.Blog blog1 = new com.swp391.OnlineLearning.model.Blog(null, "CÃ¡ch Tá»± Há»c IELTS Writing LÃªn 7.0 á»ž NhÃ ", imgUrl, "Kinh nghiá»‡m rÃ¨n luyá»‡n ká»¹ nÄƒng viáº¿t IELTS táº¡i nhÃ  mÃ  khÃ´ng cáº§n Ä‘áº¿n trung tÃ¢m.", "Trong bÃ i viáº¿t nÃ y, chÃºng ta sáº½ cÃ¹ng Ä‘i sÃ¢u vÃ o phÆ°Æ¡ng phÃ¡p paraphrase vÃ  cÃ¡ch lÃªn dÃ n Ã½ nhanh Ä‘á»ƒ ghi trá»n Ä‘iá»ƒm IELTS Writing...", b1, marketing);
				blog1.setStatus(com.swp391.OnlineLearning.model.Blog.BlogStatus.PUBLISHED);
				com.swp391.OnlineLearning.model.Blog blog2 = new com.swp391.OnlineLearning.model.Blog(null, "5 BÆ°á»›c Äá»ƒ NÃ³i Tiáº¿ng Anh TrÃ´i Cháº£y Tá»± NhiÃªn", imgUrl, "BÃ­ kÃ­p giÃºp báº¡n nÃ¢ng cao pháº£n xáº¡ khi trÃ² chuyá»‡n vá»›i ngÆ°á»i nÆ°á»›c ngoÃ i.", "Äá»«ng quÃ¡ táº­p trung vÃ o ngá»¯ phÃ¡p khi nÃ³i, hÃ£y báº¯t chÆ°á»›c cÃ¡ch phÃ¡t Ã¢m (shadowing) Ä‘á»ƒ rÃ¨n luyá»‡n thÃ³i quen pháº£n xáº¡ tá»± nhiÃªn cá»§a nÃ£o bá»™...", b1, marketing);
				blog2.setStatus(com.swp391.OnlineLearning.model.Blog.BlogStatus.PUBLISHED);
				com.swp391.OnlineLearning.model.Blog blog3 = new com.swp391.OnlineLearning.model.Blog(null, "CÃ¡c Lá»—i Ngá»¯ PhÃ¡p ThÆ°á»ng Gáº·p Trong BÃ i Thi TOEIC", imgUrl, "PhÃ¢n loáº¡i cÃ¡c báº«y ngá»¯ phÃ¡p phá»• biáº¿n vÃ  cÃ¡ch trÃ¡nh máº¥t Ä‘iá»ƒm oan.", "Pháº§n thi Reading cá»§a TOEIC luÃ´n áº©n chá»©a vÃ´ sá»‘ rá»§i ro vá»›i cÃ¡c máº«u cÃ¢u Ä‘áº£o ngá»¯ vÃ  thÃ¬ hoÃ n thÃ nh. HÃ£y cÃ¹ng Ä‘iá»ƒm qua...", b2, marketing);
				blog3.setStatus(com.swp391.OnlineLearning.model.Blog.BlogStatus.PUBLISHED);
				blogRepository.saveAll(List.of(blog1, blog2, blog3));
			}

			// Seed Sliders
			if (sliderRepository.count() == 0) {
				com.swp391.OnlineLearning.model.Slider s1 = new com.swp391.OnlineLearning.model.Slider();
				s1.setTitle("Há»‡ Thá»‘ng Tiáº¿ng Anh Trá»±c Tuyáº¿n HÃ ng Äáº§u");
				s1.setDescription("Má»Ÿ rá»™ng tiá»m nÄƒng ngoáº¡i ngá»¯ cá»§a báº¡n vá»›i cÃ¡c chuyÃªn gia uy tÃ­n.");
				s1.setImageUrl(imgUrl);
				s1.setLinkUrl("/courses");
				s1.setStatus("ACTIVE");
				s1.setOrderNumber(1);
				s1.setUser(marketing);
				
				com.swp391.OnlineLearning.model.Slider s2 = new com.swp391.OnlineLearning.model.Slider();
				s2.setTitle("Æ¯u ÄÃ£i Äáº·c Biá»‡t KhÃ³a Luyá»‡n Thi IELTS Cáº¥p Tá»‘c");
				s2.setDescription("Cam káº¿t Ä‘áº§u ra chuáº©n 6.5+ chá»‰ sau 3 thÃ¡ng. ÄÄƒng kÃ½ ngay Ä‘á»ƒ nháº­n Æ°u Ä‘Ã£i lÃªn Ä‘áº¿n 20%!");
				s2.setImageUrl(imgUrl);
				s2.setLinkUrl("/promotions");
				s2.setStatus("ACTIVE");
				s2.setOrderNumber(2);
				s2.setUser(marketing);
				
				sliderRepository.saveAll(List.of(s1, s2));
			}

			System.out.println(">>> ÄÃ£ táº¡o dá»¯ liá»‡u máº«u thÃ nh cÃ´ng vá»›i Ä‘áº§y Ä‘á»§ Courses, Blogs, Sliders!");

		};
	}
}
