package com.ecommerce.productmodule.controllers;

import com.ecommerce.pojo.ProductDTO;
import com.ecommerce.pojo.ResponseDTO;
import com.ecommerce.productmodule.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value="/createProduct")
    public ResponseEntity<ResponseDTO> insertProduct(@RequestBody ProductDTO productDTO) {
        ResponseDTO response = new ResponseDTO();
        boolean product = productService.insertProduct(productDTO);
        if(product) {
            response.setCode("200");
            response.setMessage("Product successfully inserted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            response.setCode("400");
            response.setMessage("Product insertion unsuccessfull");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
