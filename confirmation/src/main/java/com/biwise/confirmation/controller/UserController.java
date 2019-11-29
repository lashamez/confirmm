package com.biwise.confirmation.controller;

import com.biwise.confirmation.config.ExceptionAdvice;
import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.ui.request.LoginModel;
import com.biwise.confirmation.ui.response.ApiResponse;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.request.UserRequestModel;
import com.biwise.confirmation.ui.response.Message;
import com.biwise.confirmation.ui.response.MessageType;
import com.biwise.confirmation.ui.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<UserRest> saveUser(@RequestBody UserRequestModel user) {
        if (userService.findOne(user.getUserName()) != null) {
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), new Message( MessageType.error,"Username already exists"),  null);
        }
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto savedUser = userService.save(userDto);
        UserRest userRest = modelMapper.map(savedUser, UserRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message( MessageType.success,"User saved successfully."), userRest);
    }

    @GetMapping
    public ApiResponse<List<UserRest>> listUser() {
        List<UserDto> allUsers = userService.findAll();
        List<UserRest> userRests = new ArrayList<>();
        allUsers.forEach(userDto -> userRests.add(modelMapper.map(userDto, UserRest.class)));
        return new ApiResponse<>(HttpStatus.OK.value(), new Message( MessageType.success, "User list fetched successfully."), userRests);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserRest> getOne(@PathVariable String id) {
        UserDto userDto = userService.findByUserId(id);
        if (userDto == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), new Message(MessageType.success, "User doesn't exist"), null);
        }
        UserRest returnValue = modelMapper.map(userDto, UserRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.success,"User fetched successfully."), returnValue);
    }
    @PutMapping("/{id}")
    public ApiResponse<UserRest> update(@PathVariable String id, @RequestBody UserRequestModel userRequestModel) {
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        userDto.setUserId(id);
        UserDto updatedUser = userService.update(userDto);
        UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.success, "User updated successfully."), returnValue);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message( MessageType.success,"User deleted successfully."), null);
    }
    @PostMapping("/login")
    public ApiResponse<UserRest> login(@RequestBody LoginModel login) {
        UserDto userDto = userService.findOne(login.getUserName());
        if (userDto != null && userDto.getPassword().equals(login.getPassword())) {
            UserRest userRest = modelMapper.map(userDto, UserRest.class);
            return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.info, String.format("Welcome %s", userRest.getUserName())), userRest);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), new Message(MessageType.error, "Invalid Credentials"), null);
    }
}