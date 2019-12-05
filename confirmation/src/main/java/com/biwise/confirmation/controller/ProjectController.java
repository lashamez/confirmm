package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.ProjectDto;
import com.biwise.confirmation.service.ProjectService;
import com.biwise.confirmation.ui.request.ProjectRequestModel;
import com.biwise.confirmation.ui.response.MessageType;
import com.biwise.confirmation.ui.response.ProjectRest;
import com.biwise.confirmation.ui.response.RestMessage;
import com.biwise.confirmation.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SuppressWarnings("unchecked")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
public class ProjectController {
    private static final Logger logger = LogManager.getLogger(ProjectController.class);
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private ProjectService projectService;
    @PostMapping("")
    public ResponseEntity<RestMessage<ProjectRest>> createProject(@Valid @RequestBody ProjectRequestModel project){
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        ProjectDto savedProject = projectService.createProject(projectDto);
        ProjectRest result = modelMapper.map(savedProject, ProjectRest.class);
        return ResponseEntity.ok(new RestMessage<>(result, MessageType.info, "Project created successfully"));
    }
}