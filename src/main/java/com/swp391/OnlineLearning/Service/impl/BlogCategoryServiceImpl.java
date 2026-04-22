package com.swp391.OnlineLearning.service.impl;

import com.swp391.OnlineLearning.model.BlogCategory;
import com.swp391.OnlineLearning.repository.BlogCategoryRepository;
import com.swp391.OnlineLearning.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;
    @Override
    public List<BlogCategory> findAll() {
        return this.blogCategoryRepository.findAll();
    }
}
