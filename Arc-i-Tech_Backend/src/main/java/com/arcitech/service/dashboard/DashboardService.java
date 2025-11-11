package com.arcitech.service.dashboard;

public interface DashboardService<T> {
    T getDashboardData(String userId);
}