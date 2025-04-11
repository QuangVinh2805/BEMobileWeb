package com.example.mobile_store.controller;


import com.example.mobile_store.models.Category;
import com.example.mobile_store.models.CategoryDetail;
import com.example.mobile_store.models.Product;
import com.example.mobile_store.repository.CategoryDetailRepository;
import com.example.mobile_store.services.CategoryService;
import com.example.mobile_store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryDetailRepository categoryDetailRepository;

    @RequestMapping(value = "/allCategory", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> listAllCategory(){
        return categoryService.listAllCategory();
    }

    @RequestMapping(value = "/allCategoryDetail", method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDetail>> listAllCategoryDetail(){
        return categoryService.listAllCategoryDetail();
    }

    @GetMapping("/categoryDetail/{categoryId}")
    public ResponseEntity<List<CategoryDetail>> getByCategory(@PathVariable long categoryId) {
        List<CategoryDetail> details = categoryDetailRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(details);
    }
}
