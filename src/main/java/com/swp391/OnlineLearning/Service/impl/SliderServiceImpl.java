package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Model.Slider;
import com.swp391.OnlineLearning.Model.dto.SliderCreateUpdateDto;
import com.swp391.OnlineLearning.Model.dto.SliderDTO;
import com.swp391.OnlineLearning.Model.enums.SliderStatus;
import com.swp391.OnlineLearning.Repository.SliderRepository;
import com.swp391.OnlineLearning.Service.SliderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderServiceImpl implements SliderService {

    private final SliderRepository sliderRepository;

    public SliderServiceImpl(SliderRepository sliderRepository) {
        this.sliderRepository = sliderRepository;
    }

    @Override
    public Page<Slider> getSliders(String keyword, String status, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return sliderRepository.searchSliders(keyword, status, pageable);
    }

    @Override
    public List<Slider> getActiveSliders() {
        return sliderRepository.findByStatusOrderByOrderNumberAsc(SliderStatus.SHOW.name());
    }

    @Override
    public Slider getSliderById(Long id) {
        return sliderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slider not found with id: " + id));
    }

    @Override
    public Slider createSlider(SliderCreateUpdateDto dto) {
        Slider slider = new Slider();
        slider.setTitle(dto.getTitle());
        slider.setDescription(dto.getDescription());
        slider.setLinkUrl(dto.getLinkUrl());
        slider.setOrderNumber(dto.getOrderNumber() != null ? dto.getOrderNumber() : 1);
        slider.setStatus(dto.getStatus() != null ? dto.getStatus() : SliderStatus.HIDE.name());
        slider.setImageUrl(dto.getImageUrl());
        return slider;
    }

    @Override
    public Slider updateSlider(Long id, SliderDTO dto) {
        Slider slider = getSliderById(id);
        slider.setTitle(dto.getTitle());
        slider.setDescription(dto.getDescription());
        slider.setLinkUrl(dto.getLinkUrl());
        slider.setOrderNumber(dto.getOrderNumber());
        slider.setStatus(dto.getStatus());
        if (dto.getImageUrl() != null) {
            slider.setImageUrl(dto.getImageUrl());
        }
        return sliderRepository.save(slider);
    }

    @Override
    public Slider updateSliderWithFile(Long id, SliderCreateUpdateDto dto) {
        Slider slider = getSliderById(id);
        slider.setTitle(dto.getTitle());
        slider.setDescription(dto.getDescription());
        slider.setLinkUrl(dto.getLinkUrl());
        slider.setOrderNumber(dto.getOrderNumber());
        if (dto.getStatus() != null) {
            slider.setStatus(dto.getStatus());
        }
        if (dto.getImageUrl() != null) {
            slider.setImageUrl(dto.getImageUrl());
        }
        return sliderRepository.save(slider);
    }

    @Override
    public Slider toggleStatus(Long id, String status) {
        Slider slider = getSliderById(id);
        slider.setStatus(status);
        return sliderRepository.save(slider);
    }

    @Override
    public Slider toggleSlider(Long id) {
        Slider slider = getSliderById(id);
        if (SliderStatus.SHOW.name().equals(slider.getStatus())) {
            slider.setStatus(SliderStatus.HIDE.name());
        } else {
            slider.setStatus(SliderStatus.SHOW.name());
        }
        return sliderRepository.save(slider);
    }

    @Override
    public void deleteSlider(Long id) {
        sliderRepository.deleteById(id);
    }

    @Override
    public void incrementViewCount(Long id) {
        Slider slider = getSliderById(id);
        slider.setViewCount(slider.getViewCount() + 1);
        sliderRepository.save(slider);
    }

    @Override
    public Slider save(Slider slider) {
        return sliderRepository.save(slider);
    }
}
