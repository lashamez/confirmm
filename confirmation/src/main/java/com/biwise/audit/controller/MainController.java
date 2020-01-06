package com.biwise.audit.controller;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.service.MailService;
import com.biwise.audit.service.PackageService;
import com.biwise.audit.service.UserService;
import com.biwise.audit.ui.request.PackageRequestModel;
import com.biwise.audit.utils.HeaderUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class MainController {
    private final MailService mailService;

    private final PackageService packageService;

    private final UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MainController(MailService mailService, PackageService packageService, UserService userService) {
        this.mailService = mailService;
        this.packageService = packageService;
        this.userService = userService;
    }

    @PostMapping("/register")
    @Transactional
    ResponseEntity<Void> register(@RequestBody @Valid PackageRequestModel plan) {
        mailService.sendRegisterMail(plan.getEmail());
        PackageDto packageDto = modelMapper.map(plan, PackageDto.class);
        packageService.createPackage(packageDto);
        return ResponseEntity.noContent().headers(HeaderUtils.createEntityCreationAlert("Package", plan.getEmail())).build();
    }

}
