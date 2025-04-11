package com.example.mobile_store.services;

import com.example.mobile_store.models.Category;
import com.example.mobile_store.models.CategoryDetail;
import com.example.mobile_store.models.Product;
import com.example.mobile_store.repository.CategoryDetailRepository;
import com.example.mobile_store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryDetailRepository categoryDetailRepository;

    public ResponseEntity<List<Category>> listAllCategory(){
        List<Category> listCategory= categoryRepository.findAll();
        if(listCategory.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Category>>(listCategory, HttpStatus.OK);
    }

    public ResponseEntity<List<CategoryDetail>> listAllCategoryDetail(){
        List<CategoryDetail> listCategoryDetail= categoryDetailRepository.findAll();
        if(listCategoryDetail.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<CategoryDetail>>(listCategoryDetail, HttpStatus.OK);
    }
}
