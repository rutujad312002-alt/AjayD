package com.arcitech.service.dashboard;

import com.arcitech.dto.dashboard.*;
import java.util.List;

public interface UserDashboardService extends DashboardService<UserDashboardDTO> {
    // Common methods for all users
    void submitInquiry(String subject, String message);
    void sendMessageToTeam(String teamId, String message);
    
    // Methods for software users
    TimelineDTO getProjectTimeline(String projectId);
    List<ProgressUpdateDTO> getProgressUpdates(String projectId);
    List<DeliverableDTO> getDeliverables(String projectId);
    
    // Methods for training users
    List<TrainingSessionDTO> getScheduledSessions();
    List<LearningMaterialDTO> getLearningMaterials();
    CertificateDTO getInternshipCertificate();
    void attemptMockTest(String testId, List<TestAnswerDTO> answers);
    List<TestResultDTO> getTestResults();
    SkillProgressDTO getPersonalSkillProgress();
}