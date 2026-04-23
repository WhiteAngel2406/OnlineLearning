package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Model.Course;
import com.swp391.OnlineLearning.Model.Slider;
import com.swp391.OnlineLearning.Model.dto.BlogDTO;
import com.swp391.OnlineLearning.Model.dto.CourseFeedbackStats;
import com.swp391.OnlineLearning.Service.BlogService;
import com.swp391.OnlineLearning.Service.CourseService;
import com.swp391.OnlineLearning.Service.FeedbackService;
import com.swp391.OnlineLearning.Service.SliderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final SliderService sliderService;
    private final CourseService courseService;
    private final BlogService blogService;
    private final FeedbackService feedbackService;

    public HomeController(SliderService sliderService, CourseService courseService, BlogService blogService, FeedbackService feedbackService) {
        this.sliderService = sliderService;
        this.courseService = courseService;
        this.blogService = blogService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Slider> sliders = sliderService.getActiveSliders();
        List<Course> featuredCourses = courseService.findFeaturedCourses(3);
        List<BlogDTO> latestBlogs = blogService.findLatestBlogs(4);

        Map<Long, CourseFeedbackStats> courseFeedbackStatsMap = new HashMap<>();
        if (featuredCourses != null) {
            for (Course course : featuredCourses) {
                courseFeedbackStatsMap.put(course.getId(), feedbackService.getFeedbackStats(course.getId()));
            }
        }

        model.addAttribute("sliders", sliders);
        model.addAttribute("featuredCourses", featuredCourses);
        model.addAttribute("latestBlogs", latestBlogs);
        model.addAttribute("courseFeedbackStatsMap", courseFeedbackStatsMap);

        return "home";
    }

    @GetMapping("/sliders/view/{id}")
    public String viewSlider(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        Slider slider = sliderService.getSliderById(id);
        if (slider == null) {
            return "redirect:/";
        }
        sliderService.incrementViewCount(id);
        model.addAttribute("slider", slider);
        return "sliderDetail";
    }
}
