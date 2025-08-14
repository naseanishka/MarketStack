package com.ecommerce.ordermodule.dao;

import com.ecommerce.pojo.OrderDTO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class OrderDAO {
    private static final String url = "jdbc:mysql://localhost:3306/ecomm";
    private static final String username = "root";
    private static final String password = "CHANGE_ME";

    public static boolean addToOrder(OrderDTO orderDTO) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement checkCart = connection.prepareStatement("select cart_id from cart where user_id = ?");
            checkCart.setInt(1, orderDTO.getUser_id());
            int cartId = -1;
            int orderId = -1;

            ResultSet resultCart = checkCart.executeQuery();
            if(!resultCart.next()) {
                System.out.println("No cart exists");
                return false;
            }
            else {
                cartId = resultCart.getInt("cart_id");
            }

            // Now get the cart items.
            PreparedStatement getItems = connection.prepareStatement("select product_id, quantity from cart_items where cart_id = ?");
            getItems.setInt(1, cartId);
            ResultSet itemsResult = getItems.executeQuery();

            // Insert the cart items into order table
            PreparedStatement insertToOrder = connection.prepareStatement("insert into OrderTable (user_id) values (?)");
            insertToOrder.setInt(1, orderDTO.getUser_id());

            int rows = insertToOrder.executeUpdate();
            if(rows <= 0) {
                System.out.println("Something went wrong while creating an order");
                return false;
            }
            else {
                PreparedStatement getOrderId = connection.prepareStatement("select order_id from OrderTable where user_id = ?");
                getOrderId.setInt(1, orderDTO.getUser_id());

                ResultSet resultOrderID = getOrderId.executeQuery();
                if(!resultOrderID.next()) {
                    System.out.println("Error while placing order");
                    return false;
                } else {
                    orderId = resultOrderID.getInt("order_id");
                }
            }

            // Insert each cart item into order_items
            while(itemsResult.next()) {
                PreparedStatement insertInOI= connection.prepareStatement("Insert into Order_Items (order_id, product_id, quantity) values (?,?,?)");
                insertInOI.setInt(1, orderId);
                insertInOI.setInt(2, itemsResult.getInt(("product_id")));
                insertInOI.setInt(3, itemsResult.getInt(("quantity")));

                int result = insertInOI.executeUpdate();
                if(result <= 0) {
                    return false;
                }
            }

            PreparedStatement delete = connection.prepareStatement("delete from cart where cart_id = ?");
            delete.setInt(1, cartId);

            int deleted = delete.executeUpdate();
            if(deleted <= 0) {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error");
            return  false;
        }
        return true;
    }
}
