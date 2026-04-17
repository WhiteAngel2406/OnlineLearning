package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.Model.CourseCategory;
import com.swp391.OnlineLearning.repository.CourseCategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCategoryService {
    private final CourseCategoryRepository repository;

    public CourseCategoryService(CourseCategoryRepository repository) {
        this.repository = repository;
    }

    public Page<CourseCategory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<CourseCategory> findAll() {
        return repository.findAll();
    }

    public Page<CourseCategory> findWithFilters(String keyword, Boolean active, Pageable pageable) {
        return repository.findWithFilters(keyword, active, pageable);
    }

    public CourseCategory findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public CourseCategory save(CourseCategory category) {
        return repository.save(category);
    }

    public CourseCategory createCategory(CourseCategory category) {
        if (repository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại!");
        }
        return repository.save(category);
    }

    public CourseCategory updateCategory(Long id, CourseCategory categoryDetails) {
        CourseCategory category = findById(id);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setActive(categoryDetails.isActive());
        return repository.save(category);
    }

    public void deleteCategory(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Không thể xóa danh mục này vì đang có khóa học thuộc danh mục.");
        }
    }

    public CourseCategory update(CourseCategory category) {
        return repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }

    public long countCategories() {
        return repository.count();
    }
}
