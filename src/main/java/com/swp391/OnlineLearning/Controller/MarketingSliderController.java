package com.swp391.OnlineLearning.Controller;

import com.swp391.OnlineLearning.Model.dto.SliderCreateUpdateDto;
import com.swp391.OnlineLearning.Model.enums.SliderStatus;
import com.swp391.OnlineLearning.Service.SliderService;
import com.swp391.OnlineLearning.Service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/marketing/sliders")
public class MarketingSliderController {

    @Autowired
    private SliderService sliderService;

    @Autowired
    private UploadService uploadService;

    @GetMapping
    public String listSliders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        model.addAttribute("sliders", sliderService.getSliders(keyword, status, page, size));
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        return "marketing/slider/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("slider", new SliderCreateUpdateDto());
        return "marketing/slider/create";
    }

    @PostMapping("/create")
    public String createSlider(@ModelAttribute("slider") SliderCreateUpdateDto dto,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               RedirectAttributes redirectAttributes) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadService.uploadImage(imageFile);
                dto.setImageUrl(imageUrl);
            }
            // Mặc định ép trạng thái PENDING
            dto.setStatus(SliderStatus.PENDING.name());
            sliderService.createSlider(dto);
            // Save slider explicitly
            // Wait, createSlider in SliderServiceImpl just creates the object but doesn't save it!
            // I need to check if SliderController does sliderService.save().
            // Wait, let's look at SliderServiceImpl.
            com.swp391.OnlineLearning.Model.Slider slider = sliderService.createSlider(dto);
            sliderService.save(slider);
            
            redirectAttributes.addFlashAttribute("success", "Đã tạo slider chờ duyệt thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/marketing/sliders";
    }

    // Optional: Xóa (chỉ khi PENDING)
    @PostMapping("/delete/{id}")
    public String deleteSlider(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            com.swp391.OnlineLearning.Model.Slider slider = sliderService.getSliderById(id);
            if (SliderStatus.PENDING.name().equals(slider.getStatus()) || SliderStatus.HIDE.name().equals(slider.getStatus())) {
                sliderService.deleteSlider(id);
                redirectAttributes.addFlashAttribute("success", "Đã xóa slider thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa slider đang hiển thị!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa: " + e.getMessage());
        }
        return "redirect:/marketing/sliders";
    }
}
