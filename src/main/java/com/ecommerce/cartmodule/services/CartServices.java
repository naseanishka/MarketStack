package com.ecommerce.cartmodule.services;

import com.ecommerce.cartmodule.dao.CartDAO;
import com.ecommerce.pojo.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartServices {
    @Autowired
    CartDAO cartDAO;

    public boolean addToCart(CartDTO cartDTO) {
        return cartDAO.addToCart(cartDTO);
    }
}
