import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { Users, Building2, ClipboardList, Settings } from "lucide-react";

export default function SubAdminDashboard() {
  return (
    <DashboardLayout>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Department Users"
          value="124"
          icon={Users}
          trend={{ value: 8, trend: 'up' }}
          description="Active department users"
        />
        <StatsCard
          title="Active Projects"
          value="34"
          icon={Building2}
          trend={{ value: 5, trend: 'up' }}
          description="Current projects"
        />
        <StatsCard
          title="Tasks Overview"
          value="245"
          icon={ClipboardList}
          trend={{ value: 15, trend: 'up' }}
          description="Tasks in progress"
        />
        <StatsCard
          title="System Config"
          value="Optimized"
          icon={Settings}
          description="Department systems"
        />
      </div>
      {/* Add more sub admin specific components here */}
    </DashboardLayout>
  );
}