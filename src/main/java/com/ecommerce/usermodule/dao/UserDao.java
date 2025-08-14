package com.ecommerce.usermodule.dao;

import com.ecommerce.pojo.UserDTO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;

@Component
public class UserDao {

    private static final String url = "jdbc:mysql://localhost:3306/ecomm";
    private static final String username = "root";
    private static final String password = "CHANGE_ME";

    public boolean registerUser(UserDTO userDTO) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, age, gender, email, password, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, userDTO.getName());
            preparedStatement.setInt(2, userDTO.getAge());
            preparedStatement.setString(3, userDTO.getGender());
            preparedStatement.setString(4, userDTO.getEmail());
            preparedStatement.setString(5, userDTO.getPassword());
            preparedStatement.setString(6, userDTO.getPhone());
            preparedStatement.setString(7, userDTO.getAddress());

            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rows);

            return rows > 0;
        } catch (Exception e) {
            System.out.println("Exception while registering the user: " + e);
            return false;
        }
    }

    public String fetchUserPassword(UserDTO userDTO)  {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            preparedStatement.setString(1, userDTO.getEmail());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return rs.getString("password");
            }
            return null;
            } catch (Exception e) {
                System.out.println("Exception while fetching the password: " + e);
                return null;
            }
        }
    }
