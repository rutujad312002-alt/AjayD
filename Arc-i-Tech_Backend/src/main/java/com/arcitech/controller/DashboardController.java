package com.arcitech.controller;

import com.arcitech.dto.dashboard.*;
import com.arcitech.service.dashboard.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final SuperAdminDashboardService superAdminDashboardService;
    private final SubAdminDashboardService subAdminDashboardService;
    private final ProjectDeveloperDashboardService projectDeveloperDashboardService;
    private final TeamMemberDashboardService teamMemberDashboardService;
    private final UserDashboardService userDashboardService;

    @GetMapping("/super-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public SuperAdminDashboardDTO getSuperAdminDashboard() {
        return superAdminDashboardService.getSuperAdminDashboard();
    }

    @GetMapping("/sub-admin")
    @PreAuthorize("hasRole('SUB_ADMIN')")
    public SubAdminDashboardDTO getSubAdminDashboard() {
        return subAdminDashboardService.getSubAdminDashboard();
    }

    @GetMapping("/project-developer")
    @PreAuthorize("hasRole('PROJECT_DEVELOPER_TEAM')")
    public ProjectDeveloperDashboardDTO getProjectDeveloperDashboard() {
        return projectDeveloperDashboardService.getDashboardData(null);
    }

    @GetMapping("/team-member")
    @PreAuthorize("hasRole('TEAM_MEMBER')")
    public TeamMemberDashboardDTO getTeamMemberDashboard() {
        return teamMemberDashboardService.getDashboardData(null);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('SOFTWARE_USER', 'TRAINING_USER')")
    public UserDashboardDTO getUserDashboard() {
        return userDashboardService.getDashboardData(null);
    }
}