package com.blog.blogapi.service.impl;

import com.blog.blogapi.entities.Users;
import com.blog.blogapi.exceptions.ResourceNotFoundException;
import com.blog.blogapi.payloads.UserDto;
import com.blog.blogapi.repository.UserRepo;
import com.blog.blogapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        Users users = this.userDtoToUsers(userDto);
        Users savedUser = userRepo.save(users);
        return this.UsersToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        Users users = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", id));
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        users.setAbout(userDto.getAbout());

        Users updatedUser = userRepo.save(users);
        return this.UsersToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userId));
        return this.UsersToUserDto(users);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> usersList = userRepo.findAll();
        List<UserDto> userDtoList = usersList.stream().map(users -> this.UsersToUserDto(users)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer id) {
        Users users = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", "id", id));
        userRepo.delete(users);
    }

    private Users userDtoToUsers(UserDto userDto){
        Users users = modelMapper.map(userDto, Users.class);

/*
        Users users = new Users();
        users.setId(userDto.getId());
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        users.setAbout(userDto.getAbout());
*/
        return users;
    }

    private UserDto UsersToUserDto(Users users){

        UserDto userDto = modelMapper.map(users, UserDto.class);
/*
        UserDto userDto = new UserDto();
        userDto.setId(users.getId());
        userDto.setName(users.getName());
        userDto.setEmail(users.getEmail());
        userDto.setPassword(users.getPassword());
        userDto.setAbout(users.getAbout());
*/
        return userDto;
    }
}
