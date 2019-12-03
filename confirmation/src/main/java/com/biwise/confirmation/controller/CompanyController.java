package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.CompanyDto;
import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.event.OnInvitationCompleteEvent;
import com.biwise.confirmation.event.OnRegistrationCompleteEvent;
import com.biwise.confirmation.service.CompanyService;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.request.CompanyRequestModel;
import com.biwise.confirmation.ui.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("company")
public class CompanyController {

    private ModelMapper modelMapper = new ModelMapper();
    private final CompanyService companyService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public CompanyController(CompanyService companyService, UserService userService, BCryptPasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.companyService = companyService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("")
    public ApiResponse<CompanyRest> saveCompany(@RequestBody @Valid CompanyRequestModel company, Errors errors, Principal principal) {
        if (errors.hasErrors()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), new Message(MessageType.error, errors.getAllErrors().get(0).getDefaultMessage()), null);
        }
        if (companyService.findOne(company.getName())!=null) {
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), new Message( MessageType.error,"Username already exists"), null);
        }
        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);
        UserDto manager = userService.findOne(principal.getName());
        companyDto.setManager(manager);
        if (manager == null) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN.value(), new Message( MessageType.error,"Manager not found"), null);
        }

        companyDto.setManager(manager);

        company.getAccountantMails().forEach(accountant -> {
            UserDto employee = userService.findOne(accountant);
            if (employee == null) {
                eventPublisher.publishEvent(new OnInvitationCompleteEvent(companyDto, accountant));
            } else {
                companyDto.getAccountants().add(employee);
            }
        });

        CompanyDto savedCompany = companyService.save(companyDto);
        CompanyRest companyRest = modelMapper.map(savedCompany, CompanyRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.success, "Company created successfully"), companyRest);
    }

    @GetMapping("")
    public ApiResponse<List<CompanyRest>> getAllCompanies() {
        List<CompanyRest> result = new ArrayList<>();
        List<CompanyDto> allCompanies = companyService.findAll();
        allCompanies.forEach(companyDto -> {
            CompanyRest companyRest = modelMapper.map(companyDto, CompanyRest.class);
            result.add(companyRest);
        });
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.info, "Companies fetched successfully"), result);
    }
    @GetMapping("/{id}")
    public ApiResponse<CompanyRest> getOne(@PathVariable String id) {
        CompanyDto companyDto = companyService.findByCompanyId(id);
        if (companyDto == null) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), new Message(MessageType.error, "Company doesn't exist"), null);
        }
        CompanyRest returnValue = modelMapper.map(companyDto, CompanyRest.class);

        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.info,"Company fetched successfully."), returnValue);
    }
    @PutMapping("/{id}")
    public ApiResponse<CompanyRest> update(@PathVariable String id, @Valid @RequestBody CompanyRequestModel companyRequestModel) {
        CompanyDto companyDto = modelMapper.map(companyRequestModel, CompanyDto.class);
        companyDto.setCompanyId(id);
        CompanyDto updatedCompany = companyService.update(companyDto);
        CompanyRest returnValue = modelMapper.map(updatedCompany, CompanyRest.class);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message(MessageType.success, "Company updated successfully."), returnValue);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        companyService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), new Message( MessageType.success,"Company deleted successfully."), null);
    }

}