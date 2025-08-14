package com.ecommerce.ordermodule.controller;

import com.ecommerce.ordermodule.dao.OrderDAO;
import com.ecommerce.ordermodule.services.OrderServices;
import com.ecommerce.pojo.CartDTO;
import com.ecommerce.pojo.OrderDTO;
import com.ecommerce.pojo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/order")
public class OrderController {

    @Autowired
    OrderServices orderServices;

    @PostMapping(value="/placeOrder")

    public ResponseEntity<ResponseDTO> addToOrder(@RequestBody OrderDTO orderDTO) {
        ResponseDTO response = new ResponseDTO();
        boolean cart = orderServices.addToOrder(orderDTO);
        if(cart) {
            response.setCode("200");
            response.setMessage("Order placed!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            response.setCode("400");
            response.setMessage("Unsuccessful attempt");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
