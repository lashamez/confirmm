package com.biwise.audit.service.impl;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.domain.dto.ProjectDto;
import com.biwise.audit.domain.entity.PackageEntity;
import com.biwise.audit.domain.entity.ProjectEntity;
import com.biwise.audit.repository.PackageRepository;
import com.biwise.audit.service.PackageService;
import com.biwise.audit.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {
    private static final int PACKAGE_ID_LENGTH = 20;
    private final PackageRepository packageRepository;
    private final Utils utils;
    private ModelMapper modelMapper = new ModelMapper();
    public PackageServiceImpl(PackageRepository packageRepository, Utils utils) {
        this.packageRepository = packageRepository;
        this.utils = utils;
    }

    @Override
    public PackageDto createPackage(PackageDto packageDto) {
        packageDto.setPackageId(utils.generatePackageId(PACKAGE_ID_LENGTH));
        PackageEntity packageEntity = modelMapper.map(packageDto, PackageEntity.class);
        PackageEntity saved = packageRepository.save(packageEntity);
        return modelMapper.map(saved, PackageDto.class);
    }

    @Override
    public PackageDto findByPackageId(String id) {
        return null;
    }

    @Override
    public List<PackageDto> findAllForUser(String email) {
        return null;
    }

    @Override
    public PackageDto update(PackageDto packageDto) {
        return null;
    }

    @Override
    public void delete(PackageDto packageDto) {

    }
}
