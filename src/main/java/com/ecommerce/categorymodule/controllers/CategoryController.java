package com.ecommerce.categorymodule.controllers;

import com.ecommerce.categorymodule.services.CategoryService;
import com.ecommerce.pojo.CategoryDTO;
import com.ecommerce.pojo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping(value="/createCategory")
    public ResponseEntity<ResponseDTO> insertCategory(@RequestBody CategoryDTO categoryDTO) {
        ResponseDTO response = new ResponseDTO();
        boolean insertCat = categoryService.insertCategory(categoryDTO);
        if(insertCat) {
            response.setCode("200");
            response.setMessage("Category inserted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            response.setCode("400");
            response.setMessage("Category insertion unsuccessfully");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
