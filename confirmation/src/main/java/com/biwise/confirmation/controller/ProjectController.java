package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.ProjectDto;
import com.biwise.confirmation.service.MailService;
import com.biwise.confirmation.service.ProjectService;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.request.ProjectRequestModel;
import com.biwise.confirmation.ui.response.MessageType;
import com.biwise.confirmation.ui.response.ProjectRest;
import com.biwise.confirmation.ui.response.RestMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
public class ProjectController {
    private static final Logger logger = LogManager.getLogger(ProjectController.class);
    private ModelMapper modelMapper = new ModelMapper();
    private final ProjectService projectService;
    private final UserService userService;
    private final MailService mailService;
    public ProjectController(ProjectService projectService, UserService userService, MailService mailService) {
        this.projectService = projectService;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("")
    public ResponseEntity<RestMessage<ProjectRest>> createProject(@Valid @RequestBody ProjectRequestModel project){
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        ProjectDto savedProject = projectService.createProject(projectDto);
        ProjectRest result = modelMapper.map(savedProject, ProjectRest.class);
        result.getUsers().stream()
                .filter(user -> userService.findOne(user) == null)
                .forEach(user -> mailService.sendProjectInvitation(projectDto, user));
        return ResponseEntity.ok(new RestMessage<>(result, MessageType.info, "Project created successfully"));
    }
}