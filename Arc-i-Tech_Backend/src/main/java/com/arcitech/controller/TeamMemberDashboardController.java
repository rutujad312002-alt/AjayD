package com.arcitech.controller;

import com.arcitech.dto.dashboard.*;
import com.arcitech.service.dashboard.TeamMemberDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard/team-member")
@PreAuthorize("hasRole('TEAM_MEMBER')")
@RequiredArgsConstructor
public class TeamMemberDashboardController {
    private final TeamMemberDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<TeamMemberDashboardDTO> getDashboard(@RequestParam String userId) {
        return ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAssignedStudents() {
        return ResponseEntity.ok(dashboardService.getAssignedStudents());
    }

    @PutMapping("/students/{studentId}/record")
    public ResponseEntity<Void> updateStudentRecord(
            @PathVariable String studentId,
            @RequestBody StudentRecordDTO record) {
        dashboardService.updateStudentRecord(studentId, record);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/students/{studentId}/certificates")
    public ResponseEntity<Void> uploadCertificate(
            @PathVariable String studentId,
            @RequestBody CertificateDTO certificate) {
        dashboardService.uploadCertificate(studentId, certificate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/students/{studentId}/sessions")
    public ResponseEntity<Void> submitSessionReport(
            @PathVariable String studentId,
            @RequestBody SessionReportDTO report) {
        dashboardService.submitSessionReport(studentId, report);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/students/{studentId}/performance")
    public ResponseEntity<Void> updateStudentPerformance(
            @PathVariable String studentId,
            @RequestBody PerformanceDTO performance) {
        dashboardService.updateStudentPerformance(studentId, performance);
        return ResponseEntity.ok().build();
    }

    // Mock Interview Endpoints
    @PostMapping("/students/{studentId}/interviews")
    public ResponseEntity<Void> scheduleMockInterview(
            @PathVariable String studentId,
            @RequestBody InterviewDTO interview) {
        dashboardService.scheduleMockInterview(studentId, interview);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/interviews/{interviewId}/feedback")
    public ResponseEntity<Void> updateMockInterviewFeedback(
            @PathVariable String interviewId,
            @RequestBody FeedbackDTO feedback) {
        dashboardService.updateMockInterviewFeedback(interviewId, feedback);
        return ResponseEntity.ok().build();
    }

    // Placement Guidance Endpoints
    @PutMapping("/students/{studentId}/placement")
    public ResponseEntity<Void> updatePlacementProgress(
            @PathVariable String studentId,
            @RequestBody PlacementProgressDTO progress) {
        dashboardService.updatePlacementProgress(studentId, progress);
        return ResponseEntity.ok().build();
    }

    // Training Session Endpoints
    @PostMapping("/students/{studentId}/training")
    public ResponseEntity<Void> recordTrainingSession(
            @PathVariable String studentId,
            @RequestBody TrainingSessionDTO session) {
        dashboardService.recordTrainingSession(studentId, session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students/{studentId}/skills")
    public ResponseEntity<List<SkillProgressDTO>> getStudentSkillProgress(
            @PathVariable String studentId) {
        return ResponseEntity.ok(dashboardService.getStudentSkillProgress(studentId));
    }
}