package com.ecommerce.ordermodule.services;

import com.ecommerce.ordermodule.dao.OrderDAO;
import com.ecommerce.pojo.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServices {

    @Autowired
    OrderDAO orderDAO;

    public boolean addToOrder(OrderDTO orderDTO) {
        return orderDAO.addToOrder(orderDTO);
    }
}
