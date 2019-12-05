package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.service.MailService;
import com.biwise.confirmation.ui.errors.EmailAlreadyUsedException;
import com.biwise.confirmation.ui.request.LoginModel;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.request.UserRequestModel;
import com.biwise.confirmation.ui.response.MessageType;
import com.biwise.confirmation.ui.response.RestMessage;
import com.biwise.confirmation.ui.response.UserRest;
import com.biwise.confirmation.utils.HeaderUtils;
import com.biwise.confirmation.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class AccountController {
    private static final Logger logger = LogManager.getLogger(AccountController.class);
    private static final int TOKEN_LENGTH = 50;
    @Value("${confirmation.clientApp.name}")
    private String applicationName;
    private ModelMapper modelMapper = new ModelMapper();
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final Utils utils;
    public AccountController(UserService userService, BCryptPasswordEncoder passwordEncoder, MailService mailService, Utils utils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
        this.mailService = mailService;
    }

    @GetMapping("")
    public ResponseEntity<RestMessage<List<UserRest>>> listUser(Principal principal) {
        List<UserDto> allUsers = userService.findAll();
        List<UserRest> userRests = new ArrayList<>();
        allUsers.forEach(userDto -> userRests.add(modelMapper.map(userDto, UserRest.class)));
        return ResponseEntity.ok(new RestMessage<>(userRests, MessageType.info, "User list fetched successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id) {
        UserDto userDto = userService.findByUserId(id);
        if (userDto == null) {
            return new ResponseEntity<>(new RestMessage<>(null, MessageType.error, "User doesn't exist"), HttpStatus.NOT_FOUND);
        }
        UserRest returnValue = modelMapper.map(userDto, UserRest.class);
        return ResponseEntity.ok(new RestMessage<>(returnValue, MessageType.success,"User fetched successfully."));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestMessage<UserRest>> update(@PathVariable String id, @Valid @RequestBody UserRequestModel userRequestModel) {
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        userDto.setUserId(id);
        UserDto updatedUser = userService.update(userDto);
        UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);
        return ResponseEntity.ok(new RestMessage<>(returnValue, MessageType.success, "User updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, @AuthenticationPrincipal UserEntity user) {
        System.out.println(user);
        userService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtils.createEntityDeletionAlert(applicationName,  true,"User deleted", id)).build();
    }
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginModel login) {
        System.out.println(login);
        UserDto userDto = userService.findOne(login.getEmail());
        if (userDto != null && userDto.isEnabled() && userDto.getPassword().equals(passwordEncoder.encode(login.getPassword()))) {
            UserRest userRest = modelMapper.map(userDto, UserRest.class);
            return ResponseEntity.ok(new RestMessage(userRest, MessageType.info, String.format("Welcome %s", userRest.getEmail())));
        }
        return new ResponseEntity<>(new RestMessage(null, MessageType.error, "Invalid Credentials"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/confirm")
    public ResponseEntity confirm(@RequestParam("token") String token) {
        UserDto userDto = userService.findByActivationKey(token);
        if (userDto == null) {
            return new ResponseEntity<>(new RestMessage(null, MessageType.error, "Invalid token"), HttpStatus.REQUEST_TIMEOUT);
        }

        if (userDto.isEnabled()) {
            return new ResponseEntity<>(new RestMessage(null, MessageType.warning, "Account is already activated"), HttpStatus.BAD_REQUEST);
        }

        userDto.setEnabled(true);
        UserDto verifiedUser = userService.save(userDto);
        UserRest result = modelMapper.map(verifiedUser, UserRest.class);

        return ResponseEntity.ok(new RestMessage(result, MessageType.info, "User activated"));
    }

    @PostMapping("")
    public ResponseEntity registerUser(@RequestBody @Valid UserRequestModel user) {
        UserDto userDto = userService.findOne(user.getEmail());
        if (userDto != null) {
            throw new EmailAlreadyUsedException();
        }
        userDto = modelMapper.map(user, UserDto.class);
        String token = utils.generateConfirmationToken(TOKEN_LENGTH);
        userDto.setActivationKey(token);
        UserDto savedUser = userService.register(userDto);
        UserRest returnValue = modelMapper.map(savedUser, UserRest.class);
        mailService.sendActivationEmail(savedUser);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityCreationAlert(applicationName, true, "User", returnValue.getUserId()))
                .body(new RestMessage(returnValue, MessageType.success,"User saved successfully."));
    }
}