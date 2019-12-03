package com.biwise.confirmation.service;

import com.biwise.confirmation.domain.dto.CompanyDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompanyService {
    CompanyDto save(CompanyDto company);
    List<CompanyDto> findAll();
    void delete(String companyId);

    CompanyDto findOne(String companyName);

    CompanyDto findByCompanyId(String companyId);

    CompanyDto update(CompanyDto companyDto);

    List<CompanyDto> findByManager(String userId);
}
