package com.swp391.OnlineLearning.Service.impl;

import com.swp391.OnlineLearning.Repository.BlogCategoryRepository;
import com.swp391.OnlineLearning.Service.BlogCategoryService;
import com.swp391.OnlineLearning.Model.BlogCategory;
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
