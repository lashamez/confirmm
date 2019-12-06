package com.biwise.confirmation.controller;

import com.biwise.confirmation.domain.dto.ProjectDto;
import com.biwise.confirmation.domain.dto.UserDto;
import com.biwise.confirmation.service.MailService;
import com.biwise.confirmation.service.ProjectService;
import com.biwise.confirmation.service.UserService;
import com.biwise.confirmation.ui.request.ProjectRequestModel;
import com.biwise.confirmation.ui.response.ProjectRest;
import com.biwise.confirmation.utils.HeaderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
public class ProjectController implements IProjectController{
    private static final Logger logger = LogManager.getLogger(ProjectController.class);
    private ModelMapper modelMapper = new ModelMapper();
    private static final String ENTITY_NAME = "project";
    private final ProjectService projectService;
    private final UserService userService;
    private final MailService mailService;
    public ProjectController(ProjectService projectService, UserService userService, MailService mailService) {
        this.projectService = projectService;
        this.userService = userService;
        this.mailService = mailService;
    }
    @PostMapping("")
    public ResponseEntity<ProjectRest> createProject(@Valid @RequestBody ProjectRequestModel project){
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        ProjectDto savedProject = projectService.createProject(projectDto);
        ProjectRest result = modelMapper.map(savedProject, ProjectRest.class);
        result.getUsers().stream()
                .filter(user -> userService.findOne(user) == null)
                .forEach(user -> mailService.sendProjectInvitation(projectDto, user));
        return ResponseEntity.ok().headers(HeaderUtils.createEntityCreationAlert("Project", result.getProjectId()))
                .body(result);
    }

    @GetMapping("")
    public ResponseEntity<List<ProjectRest>> allProjects() {
        List<ProjectDto> allProjects = projectService.findAll();
        List<ProjectRest> projectRests =  allProjects.stream()
                .map(projectDto -> modelMapper.map(projectDto, ProjectRest.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(projectRests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectRest> findProjectById(@PathVariable String id) {
        ProjectDto projectDto = projectService.findByProjectId(id);
        if (projectDto == null) {
            return ResponseEntity.noContent()
                    .headers(HeaderUtils.createFailureAlert(ENTITY_NAME, id, "Project not found")).build();
        }
        ProjectRest returnValue = modelMapper.map(projectDto, ProjectRest.class);
        return ResponseEntity.ok(returnValue);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectRest>> findProjectsForUser(@PathVariable String userId) {
        UserDto userDto = userService.findByUserId(userId);
        if (userDto == null) {
            return ResponseEntity.notFound().headers(HeaderUtils
                    .createFailureAlert("user", userId, "User not found. nothing to update"))
                    .build();
        }
        List<ProjectDto> userProjects = projectService.findAllForUser(userDto.getEmail());
        List<ProjectRest> returnValue = userProjects.stream()
                .map(projectDto -> modelMapper.map(projectDto, ProjectRest.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(returnValue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectRest> updateProject(@PathVariable String id, @RequestBody ProjectRequestModel projectRequestModel) {
        ProjectDto projectDto = projectService.findByProjectId(id);
        if (projectDto == null) {
            return ResponseEntity.notFound().headers(HeaderUtils
                    .createFailureAlert(ENTITY_NAME, id, "Project not found. nothing to update"))
                    .build();
        }
        ProjectDto project = modelMapper.map(projectRequestModel, ProjectDto.class);
        ProjectDto updatedProject = projectService.update(project);
        ProjectRest updatedProjectRest = modelMapper.map(updatedProject, ProjectRest.class);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityUpdateAlert(ENTITY_NAME, id))
                .body(updatedProjectRest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        ProjectDto projectDto = projectService.findByProjectId(id);
        if (projectDto == null) {
            return ResponseEntity.notFound().headers(HeaderUtils
                    .createFailureAlert(ENTITY_NAME, id, "Project not found. nothing to delete"))
                    .build();
        }
        projectService.delete(projectDto);
        return ResponseEntity.ok().headers(HeaderUtils
                .createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}