package com.arcitech.service.dashboard;

import com.arcitech.dto.dashboard.*;
import java.util.List;

public interface SubAdminDashboardService extends DashboardService<SubAdminDashboardDTO> {
    SubAdminDashboardDTO getSubAdminDashboard();
    void createTeam(String teamName, String teamType);
    void addTeamMember(String teamId, String userId);
    void removeTeamMember(String teamId, String userId);
    void assignProject(String projectId, String teamId);
    void approveProjectUpdate(String updateId);
    void rejectProjectUpdate(String updateId, String reason);
    void approveCertificate(String certificateId);
    void rejectCertificate(String certificateId, String reason);
    void assignInquiryToTeam(String inquiryId, String teamId);
    void scheduleMeeting(String teamId, String dateTime, String agenda);
    List<ProjectReportDTO> getProjectReports();
    List<TrainingReportDTO> getTrainingReports();
    List<MockTestResultDTO> getMockTestResults();
}