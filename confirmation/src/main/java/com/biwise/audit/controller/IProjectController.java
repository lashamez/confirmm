package com.biwise.audit.controller;

import com.biwise.audit.ui.request.ProjectRequestModel;
import com.biwise.audit.ui.response.ProjectRest;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import io.swagger.annotations.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/project")
@Api(value = "Project Api")
public interface IProjectController {
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "project", value = "", dataType = "ProjectRequestModel")})
    @ApiOperation("Create project")
    @PostMapping("")
    ResponseEntity<ProjectRest> createProject(@Valid @RequestBody ProjectRequestModel project, Principal principal);

    @ApiImplicitParams(value = {})
    @ApiOperation("All Projects")
    @GetMapping("")
    ResponseEntity<List<ProjectRest>> allProjectsForUser(Principal principal);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "", dataType = "java.lang.String")})
    @ApiOperation("Get Project with custom Id")
    @GetMapping("/{id}")
    ResponseEntity<ProjectRest> findProjectById(@PathVariable String id);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "userId", value = "", dataType = "java.lang.String")})
    @ApiOperation("Dashboard projects for custom user")
    @GetMapping("/{email}")
    ResponseEntity<List<ProjectRest>> findProjectsForUser(@PathVariable String email);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "", dataType = "java.lang.String")})
    @ApiOperation("Update project")
    @PutMapping("/{id}")
    ResponseEntity<ProjectRest> updateProject(@PathVariable String id, @RequestBody ProjectRequestModel projectRequestModel);

    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "", dataType = "java.lang.String")})
    @ApiOperation("Delete project")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProject(@PathVariable String id);
}
