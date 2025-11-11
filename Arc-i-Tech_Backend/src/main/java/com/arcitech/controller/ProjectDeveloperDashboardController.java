package com.arcitech.controller;

import com.arcitech.dto.dashboard.*;
import com.arcitech.service.dashboard.ProjectDeveloperDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard/project-developer")
@PreAuthorize("hasRole('PROJECT_DEVELOPER_TEAM')")
@RequiredArgsConstructor
public class ProjectDeveloperDashboardController {
    private final ProjectDeveloperDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ProjectDeveloperDashboardDTO> getDashboard(@RequestParam String userId) {
        return ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getAssignedProjects() {
        return ResponseEntity.ok(dashboardService.getAssignedProjects());
    }

    @PutMapping("/projects/{projectId}/progress")
    public ResponseEntity<Void> updateProjectProgress(
            @PathVariable String projectId,
            @RequestParam double percentage) {
        dashboardService.updateProjectProgress(projectId, percentage);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/projects/{projectId}/deliverables")
    public ResponseEntity<Void> uploadDeliverable(
            @PathVariable String projectId,
            @RequestParam String fileType,
            @RequestBody byte[] content) {
        dashboardService.uploadDeliverable(projectId, fileType, content);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Void> addProjectTask(
            @PathVariable String projectId,
            @RequestBody TaskDTO task) {
        dashboardService.addProjectTask(projectId, task);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable String taskId,
            @RequestParam String status) {
        dashboardService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok().build();
    }

    // Web Project Endpoints
    @PutMapping("/projects/{projectId}/web/deployment")
    public ResponseEntity<Void> updateWebsiteDeployment(
            @PathVariable String projectId,
            @RequestBody DeploymentDTO deployment) {
        dashboardService.updateWebsiteDeployment(projectId, deployment);
        return ResponseEntity.ok().build();
    }

    // Mobile Project Endpoints
    @PostMapping("/projects/{projectId}/mobile/build")
    public ResponseEntity<Void> uploadMobileBuild(
            @PathVariable String projectId,
            @RequestBody BuildDTO build) {
        dashboardService.uploadMobileBuild(projectId, build);
        return ResponseEntity.ok().build();
    }

    // Desktop Project Endpoints
    @PutMapping("/projects/{projectId}/desktop/installation")
    public ResponseEntity<Void> updateDesktopInstallation(
            @PathVariable String projectId,
            @RequestBody InstallationDTO installation) {
        dashboardService.updateDesktopInstallation(projectId, installation);
        return ResponseEntity.ok().build();
    }

    // Engineering Project Endpoints
    @PutMapping("/projects/{projectId}/engineering/status")
    public ResponseEntity<Void> updateEngineeringProjectStatus(
            @PathVariable String projectId,
            @RequestBody StatusDTO status) {
        dashboardService.updateEngineeringProjectStatus(projectId, status);
        return ResponseEntity.ok().build();
    }

    // Consulting Project Endpoints
    @PostMapping("/projects/{projectId}/consulting/session")
    public ResponseEntity<Void> logConsultingSession(
            @PathVariable String projectId,
            @RequestBody ConsultingSessionDTO session) {
        dashboardService.logConsultingSession(projectId, session);
        return ResponseEntity.ok().build();
    }

    // Mentorship Project Endpoints
    @PutMapping("/projects/{projectId}/mentorship/progress")
    public ResponseEntity<Void> updateMentorshipProgress(
            @PathVariable String projectId,
            @RequestBody MentorshipProgressDTO progress) {
        dashboardService.updateMentorshipProgress(projectId, progress);
        return ResponseEntity.ok().build();
    }
}