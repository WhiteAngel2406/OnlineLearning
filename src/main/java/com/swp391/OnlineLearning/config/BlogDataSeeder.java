package com.swp391.OnlineLearning.config;

import com.swp391.OnlineLearning.model.Blog;
import com.swp391.OnlineLearning.model.BlogCategory;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.repository.BlogCategoryRepository;
import com.swp391.OnlineLearning.repository.BlogRepository;
import com.swp391.OnlineLearning.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlogDataSeeder implements CommandLineRunner {

    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final UserRepository userRepository;

    public BlogDataSeeder(BlogRepository blogRepository, BlogCategoryRepository blogCategoryRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.blogCategoryRepository = blogCategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (blogRepository.count() == 0) {
            System.out.println("====== STARTING BLOG DATA SEEDER ======");
            
            // Get an author
            List<User> users = userRepository.findAll();
            User author = null;
            if (!users.isEmpty()) {
                author = users.get(0);
            }

            // Create categories if empty
            if (blogCategoryRepository.count() == 0) {
                blogCategoryRepository.save(new BlogCategory("Tá»± há»c", "tu-hoc"));
                blogCategoryRepository.save(new BlogCategory("Kinh nghiá»‡m", "kinh-nghiem"));
                blogCategoryRepository.save(new BlogCategory("IELTS", "ielts"));
            }

            List<BlogCategory> categories = blogCategoryRepository.findAll();
            BlogCategory tuHoc = categories.stream().filter(c -> c.getSlug().equals("tu-hoc")).findFirst().orElse(categories.get(0));
            BlogCategory kinhNghiem = categories.stream().filter(c -> c.getSlug().equals("kinh-nghiem")).findFirst().orElse(categories.get(0));

            String defaultImage = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfFXRVrxEzWOUU5Z7XaFiRaV3kUGiSpQ7JMA&s";

            Blog blog1 = new Blog();
            blog1.setTitle("BÃ­ quyáº¿t há»c tiáº¿ng Anh má»—i ngÃ y");
            blog1.setShortDescription("Há»c tiáº¿ng Anh khÃ´ng khÃ³ náº¿u báº¡n biáº¿t cÃ¡ch duy trÃ¬ thÃ³i quen hÃ ng ngÃ y. HÃ£y cÃ¹ng tÃ¬m hiá»ƒu cÃ¡c bÆ°á»›c cÆ¡ báº£n.");
            blog1.setContent("Ná»™i dung chi tiáº¿t cá»§a bÃ i viáº¿t BÃ­ quyáº¿t há»c tiáº¿ng Anh má»—i ngÃ y. á»ž Ä‘Ã¢y chá»©ng minh viá»‡c luyá»‡n táº­p Ä‘á»u Ä‘áº·n quan trá»ng hÆ¡n lÃ  há»c nhá»“i nhÃ©t.");
            blog1.setThumbnail(defaultImage);
            blog1.setStatus(Blog.BlogStatus.PUBLISHED);
            blog1.setBlogCategory(tuHoc);
            blog1.setAuthor(author);

            Blog blog2 = new Blog();
            blog2.setTitle("5 ká»¹ nÄƒng giao tiáº¿p cáº§n nhá»›");
            blog2.setShortDescription("GiÃ¡o tiáº¿p thÃ nh tháº¡o khÃ´ng chá»‰ do tá»« vá»±ng mÃ  cÃ²n do sá»± tá»± tin vÃ  pháº£n xáº¡ nhanh.");
            blog2.setContent("Ná»™i dung chi tiáº¿t cá»§a bÃ i viáº¿t 5 ká»¹ nÄƒng giao tiáº¿p. HÃ£y thá»±c hÃ nh vá»›i báº¡n bÃ¨ vÃ  Ä‘á»«ng sá»£ sai.");
            blog2.setThumbnail(defaultImage);
            blog2.setStatus(Blog.BlogStatus.PUBLISHED);
            blog2.setBlogCategory(kinhNghiem);
            blog2.setAuthor(author);

            Blog blog3 = new Blog();
            blog3.setTitle("Lá»™ trÃ¬nh tá»± há»c IELTS tá»« 0 Ä‘áº¿n 6.5");
            blog3.setShortDescription("Náº¯m cháº¯c lá»™ trÃ¬nh tá»± há»c sáº½ giÃºp báº¡n tiáº¿t kiá»‡m thá»i gian vÃ  cÃ´ng sá»©c.");
            blog3.setContent("Báº¯t Ä‘áº§u báº±ng viá»‡c cá»§ng cá»‘ ngá»¯ phÃ¡p vÃ  tá»« vá»±ng cÆ¡ báº£n, sau Ä‘Ã³ táº­p trung vÃ o tá»«ng ká»¹ nÄƒng Reading, Listening...");
            blog3.setThumbnail(defaultImage);
            blog3.setStatus(Blog.BlogStatus.PUBLISHED);
            blog3.setBlogCategory(kinhNghiem);
            blog3.setAuthor(author);
            
            Blog blog4 = new Blog();
            blog4.setTitle("CÃ¡ch ghi nhá»› tá»« vá»±ng hiá»‡u quáº£");
            blog4.setShortDescription("PhÆ°Æ¡ng phÃ¡p spaced repetition (láº·p láº¡i ngáº¯t quÃ£ng) lÃ  cá»©u cÃ¡nh cho ngÆ°á»i há»c tá»« vá»±ng.");
            blog4.setContent("Sá»­ dá»¥ng tháº» flashcard vÃ  Ã´n táº­p theo chu ká»³ sáº½ giÃºp tá»« vá»±ng in sÃ¢u vÃ o trÃ­ nhá»› dÃ i háº¡n.");
            blog4.setThumbnail(defaultImage);
            blog4.setStatus(Blog.BlogStatus.PUBLISHED);
            blog4.setBlogCategory(tuHoc);
            blog4.setAuthor(author);

            blogRepository.save(blog1);
            blogRepository.save(blog2);
            blogRepository.save(blog3);
            blogRepository.save(blog4);

            System.out.println("====== BLOG DATA SEEDED ======");
        }
    }
}
