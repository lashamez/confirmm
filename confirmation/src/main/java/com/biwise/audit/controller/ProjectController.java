package com.biwise.audit.controller;

import com.biwise.audit.domain.dto.ProjectDto;
import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.service.MailService;
import com.biwise.audit.service.ProjectService;
import com.biwise.audit.service.UserService;
import com.biwise.audit.ui.request.ProjectRequestModel;
import com.biwise.audit.ui.response.ProjectRest;
import com.biwise.audit.utils.HeaderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<ProjectRest> createProject(@Valid @RequestBody ProjectRequestModel project, Principal principal){
        System.out.println(project);
        LocalDate startDate = LocalDate.parse(project.getStartYear());
        LocalDate endDate = LocalDate.parse(project.getEndYear());
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        projectDto.setStartYear(startDate);
        projectDto.setEndYear(endDate);
        projectDto.setUsers(Arrays.asList(principal.getName()));
        ProjectDto savedProject = projectService.createProject(projectDto);
        ProjectRest result = modelMapper.map(savedProject, ProjectRest.class);
        result.getUsers().stream()
                .filter(user -> userService.findOne(user) == null)
                .forEach(user -> mailService.sendProjectInvitation(projectDto, user));
        return ResponseEntity.ok().headers(HeaderUtils.createEntityCreationAlert("Project", result.getProjectId()))
                .body(result);
    }

    @GetMapping("")
    public ResponseEntity<List<ProjectRest>> allProjectsForUser(Principal principal) {
        List<ProjectDto> allProjects = projectService.findAllForUser(principal.getName());
        List<ProjectRest> projectRests = new ArrayList<>();
        allProjects.forEach(projectDto -> {
            ProjectRest projectRest = modelMapper.map(projectDto, ProjectRest.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedStart = projectDto.getStartYear().format(formatter);
            projectRest.setStartYear(formattedStart);
            String formattedEnd = projectDto.getEndYear().format(formatter);
            projectRest.setEndYear(formattedEnd);
            projectRests.add(projectRest);
        });
        System.out.println(projectRests);
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
        project.setProjectId(id);
        project.setStartYear(LocalDate.parse(projectRequestModel.getStartYear()));
        project.setEndYear(LocalDate.parse(projectRequestModel.getEndYear()));
        ProjectDto updatedProject = projectService.update(project);
        ProjectRest updatedProjectRest = modelMapper.map(updatedProject, ProjectRest.class);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityUpdateAlert(ENTITY_NAME, id))
                .body(updatedProjectRest);
    }

    @DeleteMapping("/{id}")
    @Transactional
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