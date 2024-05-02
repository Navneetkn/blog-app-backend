package com.blog.blogapi.service;

import com.blog.blogapi.payloads.UserDto;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer id);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer id);
}
