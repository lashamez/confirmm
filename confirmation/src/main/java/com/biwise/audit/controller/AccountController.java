package com.biwise.audit.controller;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.domain.dto.ProjectDto;
import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.service.PackageService;
import com.biwise.audit.service.UsernameAlreadyUsedException;
import com.biwise.audit.ui.errors.EmailAlreadyUsedException;
import com.biwise.audit.ui.errors.NotAllowedToRegisterException;
import com.biwise.audit.ui.request.InvitedUserRequestModel;
import com.biwise.audit.ui.request.LoginModel;
import com.biwise.audit.service.UserService;
import com.biwise.audit.ui.request.PackageRequestModel;
import com.biwise.audit.ui.request.UserRequestModel;
import com.biwise.audit.ui.response.UserRest;
import com.biwise.audit.utils.HeaderUtils;
import com.biwise.audit.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class AccountController implements IAccountController{
    private static final Logger logger = LogManager.getLogger(AccountController.class);
    @Value("${audit.clientApp.name}")
    private String applicationName;
    @Value("${invitedUserPassword}")
    private String defaultPassword;
    private static final String ENTITY_NAME = "user";
    private ModelMapper modelMapper = new ModelMapper();
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PackageService packageService;
    public AccountController(UserService userService, BCryptPasswordEncoder passwordEncoder, PackageService packageService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.packageService = packageService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserRest>> listUser(Principal principal) {
        List<UserDto> allUsers = userService.findAll();
        List<UserRest> userRests = new ArrayList<>();
        allUsers.forEach(userDto -> userRests.add(modelMapper.map(userDto, UserRest.class)));
        return ResponseEntity.ok(userRests);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id) {
        System.out.println("here");
        UserDto userDto = userService.findByUserId(id);
        if (userDto == null) {
            throw new UsernameNotFoundException(id);
        }
        UserRest returnValue = modelMapper.map(userDto, UserRest.class);
        return ResponseEntity.ok(returnValue);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserRest> update(@PathVariable String id, @Valid @RequestBody UserRequestModel userRequestModel) {
        UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
        userDto.setUserId(id);
        UserDto updatedUser = userService.update(userDto);
        UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);

        return ResponseEntity.ok().headers(HeaderUtils.createEntityUpdateAlert(ENTITY_NAME, returnValue.getUserId()))
                .body(returnValue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtils.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginModel login) {
        System.out.println("asdasda");
        UserDto userDto = userService.findOne(login.getEmail());
        if (userDto != null && userDto.isEnabled() && userDto.getPassword().equals(passwordEncoder.encode(login.getPassword()))) {
            UserRest userRest = modelMapper.map(userDto, UserRest.class);
            return ResponseEntity.ok(userRest);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/confirm")
    public ResponseEntity confirm(@RequestParam("token") String token) {
        UserDto userDto = userService.findByActivationKey(token);
        if (userDto == null) {
            return ResponseEntity.noContent().headers(HeaderUtils
                    .createFailureAlert(ENTITY_NAME, token, "Invalid activation key")).build();
        }

        if (userDto.isEnabled()) {
            return ResponseEntity.noContent().headers(HeaderUtils
                    .createFailureAlert(ENTITY_NAME, token, "User already activated")).build();
        }

        userDto.setEnabled(true);
        UserDto verifiedUser = userService.save(userDto);
        UserRest result = modelMapper.map(verifiedUser, UserRest.class);

        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity registerUser(@RequestBody @Valid UserRequestModel user) {
        UserDto userDto = userService.findOne(user.getEmail());
        if (userDto != null) {
            throw new EmailAlreadyUsedException();
        }
        userDto = userService.findByUsername(user.getUsername());
        if (userDto != null) {
            throw new UsernameAlreadyUsedException();
        }
        PackageDto packageDto = packageService.findForUser(user.getEmail());
        if (packageDto == null) {
            throw new NotAllowedToRegisterException();
        }
        userDto = modelMapper.map(user, UserDto.class);
        userDto.setCurrentPlan(packageDto);
        UserDto savedUser = userService.register(userDto);
        UserRest returnValue = modelMapper.map(savedUser, UserRest.class);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityCreationAlert(ENTITY_NAME, returnValue.getUserId()))
                .body(returnValue);
    }

    @PostMapping("/invite")
    public ResponseEntity<UserRest> inviteEmployees(@RequestBody @Valid InvitedUserRequestModel user, Principal principal) {
        String currentUser = principal.getName();
        PackageDto packageDto = packageService.findForUser(currentUser);
        List<UserDto> existingUsers = userService.findAllForPackage(packageDto);
        if (existingUsers.stream().anyMatch(userDto -> userDto.getEmail().equals(user.getEmail()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(HeaderUtils.createFailureAlert("Invited user", user.getEmail(), "User is already invited")).build();
        }
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setCurrentPlan(packageDto);
        userDto.setPassword(defaultPassword);
        UserDto saved = userService.register(userDto);
        return ResponseEntity.ok(modelMapper.map(saved, UserRest.class));
    }
    @GetMapping("/team")
    public ResponseEntity<List<UserRest>> teamMembers(Principal principal) {
        String user = principal.getName();
        UserDto userDto = userService.findOne(user);
        PackageDto packageDto = userDto.getCurrentPlan();
        System.out.println(packageDto);
        List<UserRest> userRests = packageDto.getUsers().stream().map(member -> modelMapper.map(member, UserRest.class)).collect(Collectors.toList());
        return ResponseEntity.ok(userRests);
    }

}