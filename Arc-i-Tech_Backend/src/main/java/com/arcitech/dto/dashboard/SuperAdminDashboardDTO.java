package com.arcitech.dto.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuperAdminDashboardDTO {
    private Long totalUsers;
    private Long totalSubAdmins;
    private Long totalProjects;
    private Long totalTeams;
    private Long totalRevenue;
    private Long activeServices;
    private Long pendingApprovals;
    private DashboardStats stats;
}

@Data
@Builder
class DashboardStats {
    private Long dailyActiveUsers;
    private Long newRegistrations;
    private Long completedProjects;
    private Long ongoingProjects;
}