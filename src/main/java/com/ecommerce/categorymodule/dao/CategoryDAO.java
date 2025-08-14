package com.ecommerce.categorymodule.dao;

import com.ecommerce.pojo.CategoryDTO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Component
public class CategoryDAO {

    private static final String url = "jdbc:mysql://localhost:3306/ecomm";
    private static final String username = "root";
    private static final String password = "CHANGE_ME";

    public boolean insertCategory(CategoryDTO categoryDTO) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Category(category_id, category_name) VALUES(?,?)");
            preparedStatement.setInt(1, categoryDTO.getId());
            preparedStatement.setString(2, categoryDTO.getName());

            int rows = preparedStatement.executeUpdate();
            return rows>0;
        } catch (Exception e) {
            System.out.println("Exception while inserting the category: " + e);
            return false;
        }
    }
}
