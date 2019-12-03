package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.dto.VerificationTokenDto;
import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.event.OnRegistrationCompleteEvent;
import com.biwise.confirmation.service.TokenService;
import com.biwise.confirmation.ui.request.LoginModel;
import com.biwise.confirmation.ui.response.ApiResponse;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.request.UserRequestModel;
import com.biwise.confirmation.ui.response.Message;
import com.biwise.confirmation.ui.response.MessageType;
import com.biwise.confirmation.ui.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    private ModelMapper modelMapper = new ModelMapper();
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher, TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.tokenService = tokenService;
    }

    @GetMapping("")
    public ApiResponse<List<UserRest>> listUser(Principal principal) {
        List<UserDto> allUsers = userService.findAll();
        List<UserRest> userRests = new ArrayList<>();
        allUsers.forEach(userDto -> userRests.add(modelMapper.map(userDto, UserRest.class)));
        return new ApiResponse<>(HttpStatus.OK.value(), new Message( MessageType.success, "User list fetched successfully."), userRests);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserRest> getOne(@PathVariable String id) {
        UserDto userDto = userService.findByUserId(id);
        if (userDto == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), new Message(MessageType.error, "User doesn't exist"), null);
        }
        UserRest returnValue = modelMapper.map(userDto, UserRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.success,"User fetched successfully."), returnValue);
    }
    @PutMapping("/{id}")
    public ApiResponse<UserRest> update(@PathVariable String id, @Valid @RequestBody UserRequestModel userRequestModel) {
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        userDto.setUserId(id);
        UserDto updatedUser = userService.update(userDto);
        UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.success, "User updated successfully."), returnValue);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id, @AuthenticationPrincipal UserEntity user) {
        System.out.println(user);
        userService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message( MessageType.success,"User deleted successfully."), null);
    }
    @PostMapping("/login")
    public ApiResponse<UserRest> login(@Valid @RequestBody LoginModel login, Errors errors) {
        if (errors.hasErrors()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), new Message(MessageType.error, errors.getAllErrors().get(0).getDefaultMessage()), null);
        }
        UserDto userDto = userService.findOne(login.getEmail());
        if (userDto != null && userDto.getPassword().equals(passwordEncoder.encode(login.getPassword()))) {
            UserRest userRest = modelMapper.map(userDto, UserRest.class);
            return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.info, String.format("Welcome %s", userRest.getEmail())), userRest);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), new Message(MessageType.error, "Invalid Credentials"), null);
    }

    @GetMapping("/confirm")
    public ApiResponse<UserRest> confirm(@RequestParam("token") String token) {
        VerificationTokenDto verificationToken = tokenService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ApiResponse<>(HttpStatus.REQUEST_TIMEOUT.value(), new Message(MessageType.error, "Invalid token"), null);
        }

        UserDto user = verificationToken.getUser();
        if (user.isEnabled()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), new Message(MessageType.warning, "Account is already activated"), null);
        }
        if (tokenService.isExpired(verificationToken)) {
            return new ApiResponse<>(HttpStatus.REQUEST_TIMEOUT.value(), new Message(MessageType.error, "Token has expired"), null);
        }

        user.setEnabled(true);
        UserDto verifiedUser = userService.save(user);
        UserRest result = modelMapper.map(verifiedUser, UserRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.info, "User activated"), result);
    }

    @PostMapping
    public ApiResponse<UserRest> registerUser(@RequestBody @Valid UserRequestModel user, Errors errors, WebRequest request) {
        if (errors.hasErrors()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), new Message(MessageType.error, errors.getAllErrors().get(0).getDefaultMessage()), null);
        }
        if (userService.findOne(user.getEmail()) != null) {
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), new Message( MessageType.error,"Username already exists"),  null);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto savedUser = userService.save(userDto);
        UserRest userRest = modelMapper.map(savedUser, UserRest.class);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedUser, request.getLocale(), request.getContextPath()));

        return new ApiResponse<>(HttpStatus.OK.value(), new Message( MessageType.success,"User saved successfully."), userRest);
    }
}