package com.arcitech.dto.dashboard;

import com.arcitech.model.enums.ServiceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDeveloperDashboardDTO {
    private List<ProjectDetail> activeProjects;
    private List<ServiceDetail> offeredServices;
    private List<MentorshipSession> upcomingMentorships;
    private List<ConsultingRequest> consultingRequests;
    private DeveloperStats stats;
}

@Data
@Builder
class ProjectDetail {
    private Long projectId;
    private String projectName;
    private ServiceType serviceType;
    private String status;
    private Double progress;
    private String clientName;
}

@Data
@Builder
class ServiceDetail {
    private ServiceType serviceType;
    private Integer activeClients;
    private Integer completedProjects;
    private Double rating;
}

@Data
@Builder
class MentorshipSession {
    private Long sessionId;
    private String projectName;
    private String scheduledTime;
    private String mentee;
}

@Data
@Builder
class ConsultingRequest {
    private Long requestId;
    private String clientName;
    private String serviceType;
    private String status;
}

@Data
@Builder
class DeveloperStats {
    private Integer totalProjects;
    private Integer activeProjects;
    private Double averageRating;
    private Integer completedMentorships;
}