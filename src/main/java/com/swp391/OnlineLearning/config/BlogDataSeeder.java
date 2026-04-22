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
                blogCategoryRepository.save(new BlogCategory("Tự học", "tu-hoc"));
                blogCategoryRepository.save(new BlogCategory("Kinh nghiệm", "kinh-nghiem"));
                blogCategoryRepository.save(new BlogCategory("IELTS", "ielts"));
            }

            List<BlogCategory> categories = blogCategoryRepository.findAll();
            BlogCategory tuHoc = categories.stream().filter(c -> c.getSlug().equals("tu-hoc")).findFirst().orElse(categories.get(0));
            BlogCategory kinhNghiem = categories.stream().filter(c -> c.getSlug().equals("kinh-nghiem")).findFirst().orElse(categories.get(0));

            String defaultImage = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfFXRVrxEzWOUU5Z7XaFiRaV3kUGiSpQ7JMA&s";

            Blog blog1 = new Blog();
            blog1.setTitle("Bí quyết học tiếng Anh mỗi ngày");
            blog1.setShortDescription("Học tiếng Anh không khó nếu bạn biết cách duy trì thói quen hàng ngày. Hãy cùng tìm hiểu các bước cơ bản.");
            blog1.setContent("Nội dung chi tiết của bài viết Bí quyết học tiếng Anh mỗi ngày. Ở đây chứng minh việc luyện tập đều đặn quan trọng hơn là học nhồi nhét.");
            blog1.setThumbnail(defaultImage);
            blog1.setStatus(Blog.BlogStatus.PUBLISHED);
            blog1.setBlogCategory(tuHoc);
            blog1.setAuthor(author);

            Blog blog2 = new Blog();
            blog2.setTitle("5 kỹ năng giao tiếp cần nhớ");
            blog2.setShortDescription("Giáo tiếp thành thạo không chỉ do từ vựng mà còn do sự tự tin và phản xạ nhanh.");
            blog2.setContent("Nội dung chi tiết của bài viết 5 kỹ năng giao tiếp. Hãy thực hành với bạn bè và đừng sợ sai.");
            blog2.setThumbnail(defaultImage);
            blog2.setStatus(Blog.BlogStatus.PUBLISHED);
            blog2.setBlogCategory(kinhNghiem);
            blog2.setAuthor(author);

            Blog blog3 = new Blog();
            blog3.setTitle("Lộ trình tự học IELTS từ 0 đến 6.5");
            blog3.setShortDescription("Nắm chắc lộ trình tự học sẽ giúp bạn tiết kiệm thời gian và công sức.");
            blog3.setContent("Bắt đầu bằng việc củng cố ngữ pháp và từ vựng cơ bản, sau đó tập trung vào từng kỹ năng Reading, Listening...");
            blog3.setThumbnail(defaultImage);
            blog3.setStatus(Blog.BlogStatus.PUBLISHED);
            blog3.setBlogCategory(kinhNghiem);
            blog3.setAuthor(author);
            
            Blog blog4 = new Blog();
            blog4.setTitle("Cách ghi nhớ từ vựng hiệu quả");
            blog4.setShortDescription("Phương pháp spaced repetition (lặp lại ngắt quãng) là cứu cánh cho người học từ vựng.");
            blog4.setContent("Sử dụng thẻ flashcard và ôn tập theo chu kỳ sẽ giúp từ vựng in sâu vào trí nhớ dài hạn.");
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
