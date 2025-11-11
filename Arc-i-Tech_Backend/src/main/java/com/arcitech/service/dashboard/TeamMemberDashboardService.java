package com.arcitech.service.dashboard;

import com.arcitech.dto.dashboard.*;
import java.util.List;

public interface TeamMemberDashboardService extends DashboardService<TeamMemberDashboardDTO> {
    List<StudentDTO> getAssignedStudents();
    void updateStudentRecord(String studentId, StudentRecordDTO record);
    void uploadCertificate(String studentId, CertificateDTO certificate);
    void submitSessionReport(String studentId, SessionReportDTO report);
    void updateStudentPerformance(String studentId, PerformanceDTO performance);
    void sendMessageToSubAdmin(String message);
    
    // Service-specific methods
    void scheduleMockInterview(String studentId, InterviewDTO interview);
    void updateMockInterviewFeedback(String interviewId, FeedbackDTO feedback);
    void updatePlacementProgress(String studentId, PlacementProgressDTO progress);
    void recordTrainingSession(String studentId, TrainingSessionDTO session);
    List<SkillProgressDTO> getStudentSkillProgress(String studentId);
}