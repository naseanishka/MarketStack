package com.ecommerce.categorymodule.services;

import com.ecommerce.categorymodule.dao.CategoryDAO;
import com.ecommerce.pojo.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

    public boolean insertCategory(CategoryDTO categoryDTO) {
        return categoryDAO.insertCategory(categoryDTO);
    }

}
