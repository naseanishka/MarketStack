package com.ecommerce.productmodule.dao;

import com.ecommerce.pojo.ProductDTO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class ProductDAO {
    private static final String url = "jdbc:mysql://localhost:3306/ecomm";
    private static final String username = "root";
    private static final String password = "CHANGE_ME";

    public boolean insertProduct(ProductDTO productDTO) {
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement checkCategory = connection.prepareStatement("select 1 from category where category_id = ?");
            checkCategory.setInt(1, productDTO.getCategory_id());
            ResultSet resultCat = checkCategory.executeQuery();

            if(!resultCat.next()) {
                System.out.println("This product doesn't exist in any category");
                return false;
            }

            // Check Product_id
            PreparedStatement checkProduct = connection.prepareStatement("select 1 from product where product_id = ?");
            checkProduct.setInt(1, productDTO.getProduct_id());
            ResultSet resultProd = checkProduct.executeQuery();

            if(resultProd.next()) {
                System.out.println("This product ID already exists");
                return false;
            }

            PreparedStatement insertProduct = connection.prepareStatement("INSERT INTO Product(product_id, name, price, description, image_url) VALUES (?,?,?,?,?)");
            insertProduct.setInt(1, productDTO.getProduct_id());
            insertProduct.setString(2, productDTO.getName());
            insertProduct.setInt(3, productDTO.getPrice());
            insertProduct.setString(4, productDTO.getDescription());
            insertProduct.setString(5, productDTO.getImage_url());

            int productRows = insertProduct.executeUpdate();
            if(productRows <= 0) {
                System.out.println("Something went wrong while Product insertion");
                return false;
            }

            // Now insert data into both tables, Product and Product_Category
            PreparedStatement insertProdCat = connection.prepareStatement("Insert into Product_Category(product_id, category_id) VALUES (?,?)");
            insertProdCat.setInt(1,productDTO.getProduct_id());
            insertProdCat.setInt(2,productDTO.getCategory_id());

            int rows = insertProdCat.executeUpdate();
            if(rows <= 0) {
                System.out.println("This (product_id, category_id) pair already exists in Product_Category");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
