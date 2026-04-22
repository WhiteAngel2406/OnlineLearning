package com.swp391.OnlineLearning.controller;

import com.swp391.OnlineLearning.model.Slider;
import com.swp391.OnlineLearning.model.User;
import com.swp391.OnlineLearning.model.dto.SliderCreateUpdateDto;
import com.swp391.OnlineLearning.repository.BlogRepository;
import com.swp391.OnlineLearning.repository.CourseRepository;
import com.swp391.OnlineLearning.repository.UserRepository;
import com.swp391.OnlineLearning.service.SliderService;
import com.swp391.OnlineLearning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/marketing")
public class MarketingController {

    private final SliderService sliderService;
    private final UserService userService;
    private final BlogRepository blogRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public MarketingController(SliderService sliderService, UserService userService, BlogRepository blogRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.sliderService = sliderService;
        this.userService = userService;
        this.blogRepository = blogRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String marketingDashboard(Model model, Principal principal, HttpSession session) {
        try {
            // Láº¥y thÃ´ng tin user hiá»‡n táº¡i
//            User currentUser = userService.findByEmailAndEnabledTrue(principal.getName()).orElseThrow();
            Long userId = (Long) session.getAttribute("currentUserId");
            User currentUser = userService.getUserById(userId);
            model.addAttribute("currentUser", currentUser);

            // Láº¥y danh sÃ¡ch sliders Ä‘á»ƒ quáº£n lÃ½ (láº¥y táº¥t cáº£, khÃ´ng phÃ¢n trang)
            Page<Slider> sliderPage = sliderService.getSliders("", null, 0, 1000);
            List<Slider> sliders = sliderPage.getContent();
            model.addAttribute("sliders", sliders);

            // Thá»‘ng kÃª cÆ¡ báº£n
            long totalSliders = sliders.size();
            long activeSliders = sliders.stream().filter(s -> s.getStatus().equals("SHOW")).count();

            model.addAttribute("totalSliders", totalSliders);
            model.addAttribute("activeSliders", activeSliders);

            // New stats
            long totalBlogs = blogRepository.count();
            long publishedBlogs = blogRepository.countByStatus(com.swp391.OnlineLearning.model.Blog.BlogStatus.PUBLISHED);
            long totalCourses = courseRepository.count();
            long totalUsers = userRepository.count();

            model.addAttribute("totalBlogs", totalBlogs);
            model.addAttribute("publishedBlogs", publishedBlogs);
            model.addAttribute("totalCourses", totalCourses);
            model.addAttribute("totalUsers", totalUsers);

            return "marketing/dashboard";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/sliders")
    public String manageSliders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("currentUserId");
            User currentUser = userService.getUserById(userId);
            model.addAttribute("currentUser", currentUser);

            Page<Slider> sliderPage = sliderService.getSliders(keyword, status, page, size);
            model.addAttribute("sliderPage", sliderPage);
            model.addAttribute("keyword", keyword != null ? keyword : "");
            model.addAttribute("status", status);
            model.addAttribute("currentPage", page);
            model.addAttribute("currentSize", size);

            // TÃ­nh toÃ¡n sá»‘ trang Ä‘á»ƒ hiá»ƒn thá»‹
            int totalPages = sliderPage.getTotalPages();
            int startPage = Math.max(0, page - 2);
            int endPage = Math.min(totalPages - 1, page + 2);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("totalPages", totalPages);

            return "marketing/slider-management";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/sliders/delete/{sliderId}")
    public String deleteSlider(@PathVariable("sliderId") Long sliderId, Model model, Principal principal, HttpSession session) {
        Slider slider = sliderService.getSliderById(sliderId);
        Long userId = (Long) session.getAttribute("currentUserId");
        User currentUser = userService.getUserById(userId);
        if(slider != null&&currentUser!=null&&(currentUser.getId() == slider.getUser().getId()||currentUser.getRole().equals("ADMIN"))){
            sliderService.deleteSlider(sliderId);
        }
        return "redirect:/marketing/sliders";
    }

    // Hiá»ƒn thá»‹ form táº¡o slider má»›i
    @GetMapping("/sliders/create")
    public String createSliderForm(Model model) {
        model.addAttribute("slider", new SliderCreateUpdateDto());
        return "slider/create";
    }

    // Xá»­ lÃ½ táº¡o slider má»›i
    @PostMapping("/sliders/create")
    public String createSlider(@ModelAttribute SliderCreateUpdateDto dto, RedirectAttributes redirectAttributes) {
        try {
            sliderService.createSlider(dto);
            redirectAttributes.addFlashAttribute("success", "Táº¡o slider thÃ nh cÃ´ng!");
            return "redirect:/marketing/sliders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lá»—i khi táº¡o slider: " + e.getMessage());
            return "redirect:/marketing/create";
        }
    }

    @GetMapping("/sliders/update/{id}")
    public String updateSliderForm(@PathVariable Long id, Model model) {
        try {
            Slider slider = sliderService.getSliderById(id);
            SliderCreateUpdateDto dto = new SliderCreateUpdateDto();
            dto.setTitle(slider.getTitle());
            dto.setDescription(slider.getDescription());
            dto.setOrderNumber(slider.getOrderNumber());
            dto.setStatus(slider.getStatus());
            dto.setLinkUrl(slider.getLinkUrl());

            model.addAttribute("slider", dto);
            model.addAttribute("sliderId", id);
            model.addAttribute("currentImageUrl", slider.getImageUrl());
            return "slider/update";
        } catch (Exception e) {
            return "redirect:/marketing/sliders?error=" + e.getMessage();
        }
    }

    // Xá»­ lÃ½ cáº­p nháº­t slider
    @PostMapping("/sliders/update/{id}")
    public String updateSlider(@PathVariable Long id, @ModelAttribute SliderCreateUpdateDto dto, RedirectAttributes redirectAttributes) {
        try {
            sliderService.updateSliderWithFile(id, dto);
            redirectAttributes.addFlashAttribute("success", "Cáº­p nháº­t slider thÃ nh cÃ´ng!");
            return "redirect:/marketing/sliders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lá»—i khi cáº­p nháº­t slider: " + e.getMessage());
            return "redirect:/marketing/sliders/update/" + id;
        }
    }
    @GetMapping("/sliders/sendApprove/{sliderId}")
    public String sendApproveRequest(@PathVariable Long sliderId){
        Slider slider = sliderService.getSliderById(sliderId);
        if(slider!=null&&slider.getStatus().equals("HIDE")){
        slider.setStatus("PENDING");
        sliderService.save(slider);
        }
        return "redirect:/marketing/sliders";
    }

}
