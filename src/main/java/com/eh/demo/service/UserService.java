package com.eh.demo.service;

import com.eh.demo.entity.User;

public interface UserService {

    User findByUsernameAndPassword(String username, String password);

}
