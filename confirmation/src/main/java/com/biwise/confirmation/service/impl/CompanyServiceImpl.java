package com.biwise.confirmation.service.impl;

import com.biwise.confirmation.domain.dto.CompanyDto;
import com.biwise.confirmation.domain.entity.CompanyEntity;
import com.biwise.confirmation.domain.entity.UserEntity;
import com.biwise.confirmation.repository.CompanyRepository;
import com.biwise.confirmation.service.CompanyService;
import com.biwise.confirmation.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private Utils utils;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CompanyDto save(CompanyDto company) {
        CompanyEntity newCompany = modelMapper.map(company, CompanyEntity.class);
        newCompany.setCompanyId(utils.generateCompanyId(30));
        CompanyEntity createdCompany = companyRepository.save(newCompany);
        return modelMapper.map(createdCompany, CompanyDto.class);
    }

    @Override
    public List<CompanyDto> findAll() {
        List<CompanyDto> companies = new ArrayList<>();
        companyRepository.findAll().forEach(companyEntity -> companies.add(modelMapper.map(companyEntity, CompanyDto.class)));
        return companies;
    }

    @Override
    public void delete(String companyId) {
        companyRepository.deleteByCompanyId(companyId);
    }

    @Override
    public CompanyDto findOne(String companyName) {
        Optional<CompanyEntity> companyEntity = companyRepository.findByName(companyName);
        return companyEntity.map(entity -> modelMapper.map(entity, CompanyDto.class)).orElse(null);
    }

    @Override
    public CompanyDto findByCompanyId(String companyId) {
        Optional<CompanyEntity> companyEntity = companyRepository.findByCompanyId(companyId);
        return companyEntity.map(entity -> modelMapper.map(entity, CompanyDto.class)).orElse(null);
    }

    @Override
    public CompanyDto update(CompanyDto companyDto) {
        Optional<CompanyEntity> companyEntity = companyRepository.findByCompanyId(companyDto.getCompanyId());
        if (companyEntity.isPresent()) {
            CompanyEntity entity = companyEntity.get();
            entity.setManager(modelMapper.map(companyDto.getManager(), UserEntity.class));
            entity.setAccountants(companyDto
                    .getAccountants().stream()
                    .map(userDto -> modelMapper.map(userDto, UserEntity.class))
                    .collect(Collectors.toList()));
            entity.setPhoneNumber(companyDto.getPhoneNumber());
            CompanyEntity updatedCompany = companyRepository.save(entity);
            return modelMapper.map(updatedCompany, CompanyDto.class);
        }
        return null;
    }

    @Override
    public List<CompanyDto> findByManager(String userId) {
        List<CompanyEntity> managerCompanies = companyRepository.findByManagerUserId(userId);
        return managerCompanies.stream().map(entity -> modelMapper.map(entity, CompanyDto.class)).collect(Collectors.toList());
    }
}
