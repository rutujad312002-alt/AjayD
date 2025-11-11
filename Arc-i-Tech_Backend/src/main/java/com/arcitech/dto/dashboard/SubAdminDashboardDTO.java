package com.arcitech.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubAdminDashboardDTO {
    private Long totalTeams;
    private Long totalProjects;
    private Long activeProjects;
    private List<TeamSummary> teams;
    private List<ProjectSummary> recentProjects;
    private List<ApprovalRequest> pendingApprovals;
}

@Data
@Builder
class TeamSummary {
    private Long teamId;
    private String teamName;
    private Integer memberCount;
    private Integer activeProjects;
}

@Data
@Builder
class ProjectSummary {
    private Long projectId;
    private String projectName;
    private String status;
    private String teamName;
}

@Data
@Builder
class ApprovalRequest {
    private Long requestId;
    private String type;
    private String description;
    private String requestedBy;
    private String status;
}