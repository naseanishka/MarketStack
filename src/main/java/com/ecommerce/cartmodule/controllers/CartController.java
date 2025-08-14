package com.ecommerce.cartmodule.controllers;


import com.ecommerce.cartmodule.services.CartServices;
import com.ecommerce.pojo.CartDTO;
import com.ecommerce.pojo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/cart")
public class CartController {

    @Autowired
    CartServices cartServices;

    @PostMapping(value="/addToCart")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO cartDTO) {
        ResponseDTO response = new ResponseDTO();
        boolean cart = cartServices.addToCart(cartDTO);
        if(cart) {
            response.setCode("200");
            response.setMessage("Successfully added to Cart");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            response.setCode("400");
            response.setMessage("Unsuccessful attempt");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}

