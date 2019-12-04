package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.domain.dto.VerificationTokenDto;
import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.service.MailService;
import com.biwise.confirmation.service.TokenService;
import com.biwise.confirmation.ui.errors.EmailAlreadyUsedException;
import com.biwise.confirmation.ui.request.LoginModel;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.request.UserRequestModel;
import com.biwise.confirmation.ui.response.MessageType;
import com.biwise.confirmation.ui.response.RestMessage;
import com.biwise.confirmation.ui.response.UserRest;
import com.biwise.confirmation.utils.HeaderUtils;
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
    @Value("${confirmation.clientApp.name}")
    private String applicationName;
    private ModelMapper modelMapper = new ModelMapper();
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MailService mailService;

    public AccountController(UserService userService, BCryptPasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher, TokenService tokenService, MailService mailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.tokenService = tokenService;
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
        UserDto userDto = userService.findOne(login.getEmail());
        if (userDto != null && userDto.getPassword().equals(passwordEncoder.encode(login.getPassword()))) {
            UserRest userRest = modelMapper.map(userDto, UserRest.class);
            return ResponseEntity.ok(new RestMessage(userRest, MessageType.info, String.format("Welcome %s", userRest.getEmail())));
        }
        return new ResponseEntity<>(new RestMessage(null, MessageType.error, "Invalid Credentials"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/confirm")
    public ResponseEntity confirm(@RequestParam("token") String token) {
        VerificationTokenDto verificationToken = tokenService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>(new RestMessage(null, MessageType.error, "Invalid token"), HttpStatus.REQUEST_TIMEOUT);
        }

        UserDto user = verificationToken.getUser();
        if (user.isEnabled()) {
            return new ResponseEntity<>(new RestMessage(null, MessageType.warning, "Account is already activated"), HttpStatus.BAD_REQUEST);
        }
        if (tokenService.isExpired(verificationToken)) {
            return new ResponseEntity<>(new RestMessage(null, MessageType.error, "Token has expired"), HttpStatus.REQUEST_TIMEOUT);
        }

        user.setEnabled(true);
        UserDto verifiedUser = userService.save(user);
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
        UserDto savedUser = userService.save(userDto);
        UserRest returnValue = modelMapper.map(savedUser, UserRest.class);
        mailService.sendActivationEmail(savedUser);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityCreationAlert(applicationName, true, "User", returnValue.getUserId()))
                .body(new RestMessage(returnValue, MessageType.success,"User saved successfully."));
    }
}