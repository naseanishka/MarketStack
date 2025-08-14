package com.ecommerce.cartmodule.dao;


import com.ecommerce.pojo.CartDTO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class CartDAO {
    private static final String url = "jdbc:mysql://localhost:3306/ecomm";
    private static final String username = "root";
    private static final String password = "CHANGE_ME";

    public boolean addToCart(CartDTO cartDTO) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement checkUser = connection.prepareStatement("select 1 from user where user_id = ?");
            checkUser.setInt(1, cartDTO.getUser_id());
            ResultSet resultUser = checkUser.executeQuery();

            PreparedStatement checkProduct = connection.prepareStatement("select 1 from product where product_id = ?");
            checkProduct.setInt(1, cartDTO.getProduct_id());
            ResultSet resultProduct = checkProduct.executeQuery();
            if(!resultUser.next()) {
                System.out.println("This user doesn't exist");
            }
            if(!resultProduct.next()) {
                System.out.println("This product doesn't exist");
            }

            // checking if user already has a cart
            PreparedStatement checkCart = connection.prepareStatement("select cart_id from cart where user_id = ?");
            checkCart.setInt(1, cartDTO.getUser_id());
            ResultSet resultCart = checkCart.executeQuery();

            int cartId = 0;

            if(resultCart.next()) {
                // insert that product in existing cart, user already has a cart
                cartId = resultCart.getInt("cart_id");
            }
            else {
                // create a new cart insert that product in new cart
                PreparedStatement insertInCart = connection.prepareStatement("insert into cart(user_id) values(?)");
                insertInCart.setInt(1, cartDTO.getUser_id());
                int rows = insertInCart.executeUpdate();

                if(rows <= 0) {
                    System.out.println("Error while creating cart for user");
                    return false;
                } else {
                    PreparedStatement preparedStatement = connection.prepareStatement("select cart_id from cart where user_id = ?");
                    preparedStatement.setInt(1, cartDTO.getUser_id());
                    ResultSet result = preparedStatement.executeQuery();
                    if(result.next()) {
                        cartId = result.getInt("cart_id");
                    } else {
                        System.out.println("Something went wrong while creating cart");
                        return false;
                    }
                }
            }
            PreparedStatement insertInCI = connection.prepareStatement("insert into cart_items (cart_id, product_id, quantity) value(?,?,?)");
            insertInCI.setInt(1, cartId);
            insertInCI.setInt(2, cartDTO.getProduct_id());
            insertInCI.setInt(3, cartDTO.getQuantity());

            int rows = insertInCI.executeUpdate();
            System.out.println("Product added to cart_items");
            return rows > 0;
            }
            catch (Exception e) {
                System.out.println("Failed to add to cart");
                return false;
            }
        }
}
