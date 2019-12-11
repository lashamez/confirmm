package com.biwise.audit.service;

import com.biwise.audit.domain.dto.PackageDto;

import java.util.List;

public interface PackageService {
    PackageDto createPackage(PackageDto packageDto);

    PackageDto findByPackageId(String id);

    PackageDto findForUser(String email);

    PackageDto update(PackageDto packageDto);

    void delete(PackageDto packageDto);

    List<PackageDto> findAll();
}
