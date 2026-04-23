package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Model.Slider;
import com.swp391.OnlineLearning.Model.User;
import com.swp391.OnlineLearning.Model.dto.SliderCreateUpdateDto;
import com.swp391.OnlineLearning.Model.dto.SliderDTO;
import com.swp391.OnlineLearning.Model.enums.SliderStatus;
import com.swp391.OnlineLearning.Service.SliderService;
import com.swp391.OnlineLearning.Service.UploadService;
import com.swp391.OnlineLearning.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping("/admin/sliders")
public class SliderController {

    private static final Logger log = LoggerFactory.getLogger(SliderController.class);

    private final SliderService sliderService;
    private final UploadService uploadService;
    private final UserService userService;

    public SliderController(SliderService sliderService, UploadService uploadService, UserService userService) {
        this.sliderService = sliderService;
        this.uploadService = uploadService;
        this.userService = userService;
    }

    @GetMapping
    public String getSliders(Model model,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "status", required = false) String status,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Slider> sliderPage = sliderService.getSliders(keyword, status, page, size);
        log.info("=== SLIDER LIST DEBUG ===");
        log.info("Total sliders: {}, Total pages: {}", sliderPage.getTotalElements(), sliderPage.getTotalPages());
        sliderPage.getContent().forEach(slider -> {
            log.info("Slider[id={}, title={}, imageUrl={}, status={}]",
                    slider.getId(), slider.getTitle(), slider.getImageUrl(), slider.getStatus());
        });
        model.addAttribute("sliders", sliderPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        return "admin/slider/list";
    }

    @GetMapping("/create")
    public String createSliderForm(Model model) {
        model.addAttribute("slider", new SliderCreateUpdateDto());
        model.addAttribute("statuses", SliderStatus.values());
        return "admin/slider/create";
    }

    @PostMapping("/create")
    public String createSlider(@ModelAttribute("slider") SliderCreateUpdateDto dto,
                               BindingResult bindingResult,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            Long userId = (Long) session.getAttribute("currentUserId");
            if (userId == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first");
                return "redirect:/admin/sliders";
            }

            User currentUser = userService.getUserById(userId);

            // Handle image upload
            MultipartFile imageFile = dto.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadService.uploadImage(imageFile, "sliders");
                dto.setImageUrl("/uploads/" + imageUrl);
            }

            Slider slider = sliderService.createSlider(dto);
            slider.setUser(currentUser);
            sliderService.save(slider);

            redirectAttributes.addFlashAttribute("success", "Tạo slider thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tạo slider: " + e.getMessage());
        }
        return "redirect:/admin/sliders";
    }

    @GetMapping("/update/{id}")
    public String updateSliderForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Slider slider = sliderService.getSliderById(id);
            SliderDTO sliderDto = new SliderDTO();
            sliderDto.setId(slider.getId().intValue());
            sliderDto.setTitle(slider.getTitle());
            sliderDto.setDescription(slider.getDescription());
            sliderDto.setLinkUrl(slider.getLinkUrl());
            sliderDto.setOrderNumber(slider.getOrderNumber());
            sliderDto.setStatus(slider.getStatus());
            sliderDto.setImageUrl(slider.getImageUrl());

            model.addAttribute("slider", sliderDto);
            model.addAttribute("statuses", SliderStatus.values());
            return "admin/slider/update";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/sliders";
        }
    }

    @PostMapping("/update/{id}")
    public String updateSlider(@PathVariable Long id,
                               @ModelAttribute("slider") SliderCreateUpdateDto dto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        try {
            // Handle image upload if new file is provided
            MultipartFile imageFile = dto.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadService.uploadImage(imageFile, "sliders");
                dto.setImageUrl("/uploads/" + imageUrl);
            }

            Slider existingSlider = sliderService.getSliderById(id);
            existingSlider.setTitle(dto.getTitle());
            existingSlider.setDescription(dto.getDescription());
            existingSlider.setLinkUrl(dto.getLinkUrl());
            existingSlider.setOrderNumber(dto.getOrderNumber());
            if (dto.getStatus() != null) {
                existingSlider.setStatus(dto.getStatus());
            }
            if (dto.getImageUrl() != null) {
                existingSlider.setImageUrl(dto.getImageUrl());
            }
            sliderService.save(existingSlider);

            redirectAttributes.addFlashAttribute("success", "Cập nhật slider thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật slider: " + e.getMessage());
        }
        return "redirect:/admin/sliders";
    }

    @PostMapping("/toggle/{id}")
    public String toggleSlider(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sliderService.toggleSlider(id);
            redirectAttributes.addFlashAttribute("success", "Thay đổi trạng thái slider thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thay đổi trạng thái slider: " + e.getMessage());
        }
        return "redirect:/admin/sliders";
    }

    @PostMapping("/delete/{id}")
    public String deleteSlider(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sliderService.deleteSlider(id);
            redirectAttributes.addFlashAttribute("success", "Xóa slider thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa slider: " + e.getMessage());
        }
        return "redirect:/admin/sliders";
    }
}
