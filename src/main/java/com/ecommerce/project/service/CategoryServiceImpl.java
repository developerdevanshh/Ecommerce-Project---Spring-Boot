package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;

import java.lang.module.ResolutionException;
import java.util.*;

import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryServiceImpl implements CategoryService{
//    private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
//        return categories;
        return categoryRepository.findAll();

    }

    @Override
    public void createCategory(Category category) {
//        category.setCategoryId(nextId++);
//        categories.add(category);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();

        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        if(category == null){
            return "Category Not Found";
        }
//        categories.remove(category);
        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId +" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);

        Category savedCategory = savedCategoryOptional
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        category.setCategoryId(categoryId);

        savedCategory = categoryRepository.save(category);
        return savedCategory;

//        List<Category> categories = categoryRepository.findAll();
//        Optional<Category> optionalCategory = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//
//        if(optionalCategory.isPresent()) {
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            Category savedCategory = categoryRepository.save(existingCategory);
//            return savedCategory ;
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
//        }
    }
}
