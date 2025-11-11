package com.arcitech.service.dashboard;

import com.arcitech.dto.dashboard.*;
import java.util.List;

public interface ProjectDeveloperDashboardService extends DashboardService<ProjectDeveloperDashboardDTO> {
    List<ProjectDTO> getAssignedProjects();
    void updateProjectProgress(String projectId, double percentage);
    void uploadDeliverable(String projectId, String fileType, byte[] content);
    void addProjectTask(String projectId, TaskDTO task);
    void updateTaskStatus(String taskId, String status);
    void addProjectNote(String projectId, String note);
    void sendMessageToSubAdmin(String message);
    
    // Service-specific methods
    void updateWebsiteDeployment(String projectId, DeploymentDTO deployment);
    void uploadMobileBuild(String projectId, BuildDTO build);
    void updateDesktopInstallation(String projectId, InstallationDTO installation);
    void updateEngineeringProjectStatus(String projectId, StatusDTO status);
    void logConsultingSession(String projectId, ConsultingSessionDTO session);
    void updateMentorshipProgress(String projectId, MentorshipProgressDTO progress);
}