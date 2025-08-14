package com.ecommerce.productmodule.services;

import com.ecommerce.pojo.ProductDTO;
import com.ecommerce.productmodule.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    @Autowired
    ProductDAO productDAO;

    public boolean insertProduct(ProductDTO productDTO) {
        return productDAO.insertProduct(productDTO);
    }
}
