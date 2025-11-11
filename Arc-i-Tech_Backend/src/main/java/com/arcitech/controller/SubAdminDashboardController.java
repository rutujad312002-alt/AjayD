package com.arcitech.controller;

import com.arcitech.dto.dashboard.*;
import com.arcitech.service.dashboard.SubAdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard/sub-admin")
@PreAuthorize("hasRole('SUB_ADMIN')")
@RequiredArgsConstructor
public class SubAdminDashboardController {
    private final SubAdminDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<SubAdminDashboardDTO> getDashboard(@RequestParam String userId) {
        return ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }

    @PostMapping("/teams")
    public ResponseEntity<Void> createTeam(
            @RequestParam String teamName,
            @RequestParam String teamType) {
        dashboardService.createTeam(teamName, teamType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/teams/{teamId}/members")
    public ResponseEntity<Void> addTeamMember(
            @PathVariable String teamId,
            @RequestParam String userId) {
        dashboardService.addTeamMember(teamId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/teams/{teamId}/members/{userId}")
    public ResponseEntity<Void> removeTeamMember(
            @PathVariable String teamId,
            @PathVariable String userId) {
        dashboardService.removeTeamMember(teamId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/projects/{projectId}/team/{teamId}")
    public ResponseEntity<Void> assignProject(
            @PathVariable String projectId,
            @PathVariable String teamId) {
        dashboardService.assignProject(projectId, teamId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/project-updates/{updateId}/approve")
    public ResponseEntity<Void> approveProjectUpdate(@PathVariable String updateId) {
        dashboardService.approveProjectUpdate(updateId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/project-updates/{updateId}/reject")
    public ResponseEntity<Void> rejectProjectUpdate(
            @PathVariable String updateId,
            @RequestParam String reason) {
        dashboardService.rejectProjectUpdate(updateId, reason);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/certificates/{certificateId}/approve")
    public ResponseEntity<Void> approveCertificate(@PathVariable String certificateId) {
        dashboardService.approveCertificate(certificateId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/certificates/{certificateId}/reject")
    public ResponseEntity<Void> rejectCertificate(
            @PathVariable String certificateId,
            @RequestParam String reason) {
        dashboardService.rejectCertificate(certificateId, reason);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/inquiries/{inquiryId}/assign")
    public ResponseEntity<Void> assignInquiryToTeam(
            @PathVariable String inquiryId,
            @RequestParam String teamId) {
        dashboardService.assignInquiryToTeam(inquiryId, teamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/meetings")
    public ResponseEntity<Void> scheduleMeeting(
            @RequestParam String teamId,
            @RequestParam String dateTime,
            @RequestParam String agenda) {
        dashboardService.scheduleMeeting(teamId, dateTime, agenda);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reports/projects")
    public ResponseEntity<List<ProjectReportDTO>> getProjectReports() {
        return ResponseEntity.ok(dashboardService.getProjectReports());
    }

    @GetMapping("/reports/training")
    public ResponseEntity<List<TrainingReportDTO>> getTrainingReports() {
        return ResponseEntity.ok(dashboardService.getTrainingReports());
    }

    @GetMapping("/reports/mock-tests")
    public ResponseEntity<List<MockTestResultDTO>> getMockTestResults() {
        return ResponseEntity.ok(dashboardService.getMockTestResults());
    }
}