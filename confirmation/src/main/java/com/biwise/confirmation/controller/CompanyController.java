package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.CompanyDto;
import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.event.OnInvitationCompleteEvent;
import com.biwise.confirmation.service.CompanyService;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.errors.CompanyAlreadyUsedException;
import com.biwise.confirmation.ui.errors.CompanyNotFoundException;
import com.biwise.confirmation.ui.request.CompanyRequestModel;
import com.biwise.confirmation.ui.response.*;
import com.biwise.confirmation.utils.HeaderUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unchecked")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("company")
public class CompanyController {
    @Value("${confirmation.clientApp.name}")
    private String applicationName;
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
    public ResponseEntity<RestMessage<CompanyRest>> saveCompany(@RequestBody @Valid CompanyRequestModel company, Principal principal) {
        if (companyService.findOne(company.getName())!=null) {
            throw new CompanyAlreadyUsedException();
        }
        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);
        UserDto manager = userService.findOne(principal.getName());
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
        return ResponseEntity.ok(new RestMessage(companyRest, MessageType.success, "Company created successfully"));
    }

    @GetMapping("")
    public ResponseEntity<RestMessage<List<CompanyRest>>> getAllCompanies() {
        List<CompanyRest> result = new ArrayList<>();
        List<CompanyDto> allCompanies = companyService.findAll();
        allCompanies.forEach(companyDto -> {
            CompanyRest companyRest = modelMapper.map(companyDto, CompanyRest.class);
            result.add(companyRest);
        });
        return ResponseEntity.ok().body(new RestMessage(result, MessageType.info,"Company fetched successfully."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestMessage<CompanyRest>> getOne(@PathVariable String id) {
        CompanyDto companyDto = companyService.findByCompanyId(id);
        if (companyDto == null) {
            throw new CompanyNotFoundException(id);
        }
        CompanyRest returnValue = modelMapper.map(companyDto, CompanyRest.class);
        return ResponseEntity.ok().body(new RestMessage(returnValue, MessageType.info,"Company fetched successfully."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestMessage<CompanyRest>> update(@PathVariable String id, @Valid @RequestBody CompanyRequestModel companyRequestModel) {
        CompanyDto companyDto = modelMapper.map(companyRequestModel, CompanyDto.class);
        companyDto.setCompanyId(id);
        CompanyDto updatedCompany = companyService.update(companyDto);
        CompanyRest returnValue = modelMapper.map(updatedCompany, CompanyRest.class);
        return ResponseEntity.ok(new RestMessage(returnValue, MessageType.success, "Company updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        companyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtils.createAlert(applicationName,  "Company deleted", id)).build();
    }

}