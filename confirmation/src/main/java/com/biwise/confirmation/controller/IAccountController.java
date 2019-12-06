package com.biwise.confirmation.controller;

import com.biwise.confirmation.ui.request.LoginModel;
import com.biwise.confirmation.ui.request.UserRequestModel;
import com.biwise.confirmation.ui.response.UserRest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import io.swagger.annotations.*;

@Api(value = "Account Api")
public interface IAccountController {

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "principal", value = "", dataType = "java.security.Principal")})
    @ApiOperation("Find all users")
    @GetMapping("")
    ResponseEntity<List<UserRest>> listUser(Principal principal);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "", dataType = "java.lang.String")})
    @ApiOperation("Find user by Email")
    @GetMapping("/{id}")
    ResponseEntity getOne(@PathVariable String id);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "", dataType = "java.lang.String"),
            @ApiImplicitParam(name = "userRequestModel", dataType = "UserRequestModel")})
    @ApiOperation("Update user")
    @PutMapping("/{id}")
    ResponseEntity<UserRest> update(@PathVariable String id, @Valid @RequestBody UserRequestModel userRequestModel);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "", dataType = "java.lang.String"),
            @ApiImplicitParam(name = "user", value = "", dataType = "UserEntity")})
    @ApiOperation("Delete user")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "login", value = "", dataType = "LoginModel")})
    @ApiOperation("Login")
    @PostMapping("/login")
    ResponseEntity login(@Valid @RequestBody LoginModel login);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "", dataType = "java.lang.String")})
    @ApiOperation("Confirm registered user")
    @GetMapping("/confirm")
    ResponseEntity confirm(@RequestParam("token") String token);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "user", value = "", dataType = "UserRequestModel")})
    @ApiOperation("Register user")
    @PostMapping("")
    ResponseEntity registerUser(@RequestBody @Valid UserRequestModel user);
}
