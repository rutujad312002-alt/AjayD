package com.arcitech.controller;

import com.arcitech.dto.dashboard.*;
import com.arcitech.service.dashboard.UserDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard/user")
@PreAuthorize("hasAnyRole('SOFTWARE_USER', 'TRAINING_USER')")
@RequiredArgsConstructor
public class UserDashboardController {
    private final UserDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<UserDashboardDTO> getDashboard(@RequestParam String userId) {
        return ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }

    @PostMapping("/inquiries")
    public ResponseEntity<Void> submitInquiry(
            @RequestParam String subject,
            @RequestParam String message) {
        dashboardService.submitInquiry(subject, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/teams/{teamId}/messages")
    public ResponseEntity<Void> sendMessageToTeam(
            @PathVariable String teamId,
            @RequestParam String message) {
        dashboardService.sendMessageToTeam(teamId, message);
        return ResponseEntity.ok().build();
    }

    // Software User Endpoints
    @GetMapping("/projects/{projectId}/timeline")
    @PreAuthorize("hasRole('SOFTWARE_USER')")
    public ResponseEntity<TimelineDTO> getProjectTimeline(@PathVariable String projectId) {
        return ResponseEntity.ok(dashboardService.getProjectTimeline(projectId));
    }

    @GetMapping("/projects/{projectId}/progress")
    @PreAuthorize("hasRole('SOFTWARE_USER')")
    public ResponseEntity<List<ProgressUpdateDTO>> getProgressUpdates(@PathVariable String projectId) {
        return ResponseEntity.ok(dashboardService.getProgressUpdates(projectId));
    }

    @GetMapping("/projects/{projectId}/deliverables")
    @PreAuthorize("hasRole('SOFTWARE_USER')")
    public ResponseEntity<List<DeliverableDTO>> getDeliverables(@PathVariable String projectId) {
        return ResponseEntity.ok(dashboardService.getDeliverables(projectId));
    }

    // Training User Endpoints
    @GetMapping("/training/sessions")
    @PreAuthorize("hasRole('TRAINING_USER')")
    public ResponseEntity<List<TrainingSessionDTO>> getScheduledSessions() {
        return ResponseEntity.ok(dashboardService.getScheduledSessions());
    }

    @GetMapping("/training/materials")
    @PreAuthorize("hasRole('TRAINING_USER')")
    public ResponseEntity<List<LearningMaterialDTO>> getLearningMaterials() {
        return ResponseEntity.ok(dashboardService.getLearningMaterials());
    }

    @GetMapping("/certificates/internship")
    @PreAuthorize("hasRole('TRAINING_USER')")
    public ResponseEntity<CertificateDTO> getInternshipCertificate() {
        return ResponseEntity.ok(dashboardService.getInternshipCertificate());
    }

    @PostMapping("/tests/{testId}/attempt")
    @PreAuthorize("hasRole('TRAINING_USER')")
    public ResponseEntity<Void> attemptMockTest(
            @PathVariable String testId,
            @RequestBody List<TestAnswerDTO> answers) {
        dashboardService.attemptMockTest(testId, answers);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tests/results")
    @PreAuthorize("hasRole('TRAINING_USER')")
    public ResponseEntity<List<TestResultDTO>> getTestResults() {
        return ResponseEntity.ok(dashboardService.getTestResults());
    }

    @GetMapping("/skills/progress")
    @PreAuthorize("hasRole('TRAINING_USER')")
    public ResponseEntity<SkillProgressDTO> getPersonalSkillProgress() {
        return ResponseEntity.ok(dashboardService.getPersonalSkillProgress());
    }
}