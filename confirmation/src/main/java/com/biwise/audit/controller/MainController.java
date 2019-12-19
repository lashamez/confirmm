package com.biwise.audit.controller;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.service.MailService;
import com.biwise.audit.service.PackageService;
import com.biwise.audit.service.UserService;
import com.biwise.audit.ui.request.InvitedUserRequestModel;
import com.biwise.audit.ui.request.PackageRequestModel;
import com.biwise.audit.utils.HeaderUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class MainController {
    private final MailService mailService;
    private final PackageService packageService;
    private ModelMapper modelMapper = new ModelMapper();
    public MainController(MailService mailService, PackageService packageService) {
        this.mailService = mailService;
        this.packageService = packageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid PackageRequestModel plan) {
        mailService.sendRegisterMail(plan.getEmail());
        PackageDto packageDto = modelMapper.map(plan, PackageDto.class);
        packageService.createPackage(packageDto);
        return ResponseEntity.noContent().headers(HeaderUtils.createEntityCreationAlert("Package", plan.getEmail())).build();
    }

}
