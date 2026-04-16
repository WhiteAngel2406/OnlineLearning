package com.example.demo.service;

import com.example.demo.model.Slider;
import com.example.demo.model.enums.SliderStatus;
import com.example.demo.repository.SliderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Service
public class SliderService {

    private final SliderRepository sliderRepository;

    public SliderService(SliderRepository sliderRepository) {
        this.sliderRepository = sliderRepository;
    }

    public Page<Slider> findWithFilters(String keyword, SliderStatus status, Pageable pageable) {
        return sliderRepository.findWithFilters(keyword, status, pageable);
    }

    public Optional<Slider> findById(Long id) {
        return sliderRepository.findById(id);
    }

    public Page<Slider> findAll(Pageable pageable) {
        return sliderRepository.findAll(pageable);
    }

    public Page<Slider> findByStatus(SliderStatus status, Pageable pageable) {
        return sliderRepository.findByStatus(status, pageable);
    }

    @Transactional
    public Slider createSlider(Slider slider) {
        return sliderRepository.save(slider);
    }

    @Transactional
    public Slider updateSlider(Long id, Slider sliderDetails) {
        Slider slider = sliderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slider not found"));

        slider.setTitle(sliderDetails.getTitle());
        slider.setDescription(sliderDetails.getDescription());
        slider.setLinkUrl(sliderDetails.getLinkUrl());
        slider.setStatus(sliderDetails.getStatus());
        slider.setOrderNumber(sliderDetails.getOrderNumber());
        slider.setImageUrl(sliderDetails.getImageUrl());

        return sliderRepository.save(slider);
    }

    @Transactional
    public Slider approveSlider(Long id) {
        Slider slider = sliderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slider not found"));
        slider.setStatus(SliderStatus.APPROVED);
        return sliderRepository.save(slider);
    }

    @Transactional
    public Slider rejectSlider(Long id) {
        Slider slider = sliderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slider not found"));
        slider.setStatus(SliderStatus.REJECTED);
        return sliderRepository.save(slider);
    }

    @Transactional
    public void deleteSlider(Long id) {
        Slider slider = sliderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slider not found"));
        slider.setStatus(SliderStatus.TAKEN_DOWN);
        sliderRepository.save(slider);
    }

    public long countSliders() {
        return sliderRepository.count();
    }

    public long countByStatus(SliderStatus status) {
        return sliderRepository.findByStatus(status, PageRequest.of(0, 1)).getTotalElements();
    }
}
