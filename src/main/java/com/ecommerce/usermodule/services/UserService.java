package com.ecommerce.usermodule.services;

import com.ecommerce.pojo.UserDTO;
import com.ecommerce.usermodule.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    UserDao userDao;

    public boolean userRegisterService(UserDTO userDTO) {
        return userDao.registerUser(userDTO);
    }

    public String fetchPassword(UserDTO userDTO) {
        return userDao.fetchUserPassword(userDTO);
    }
}
