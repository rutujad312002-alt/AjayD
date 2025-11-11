import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { Users, ClipboardList, Clock, TrendingUp } from "lucide-react";

export default function ManagerDashboard() {
  return (
    <DashboardLayout>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Team Members"
          value="24"
          icon={Users}
          trend={{ value: 4, trend: 'up' }}
          description="Active team members"
        />
        <StatsCard
          title="Active Tasks"
          value="78"
          icon={ClipboardList}
          trend={{ value: 15, trend: 'up' }}
          description="Tasks in progress"
        />
        <StatsCard
          title="Project Hours"
          value="156"
          icon={Clock}
          trend={{ value: 12, trend: 'up' }}
          description="Hours this week"
        />
        <StatsCard
          title="Performance"
          value="94%"
          icon={TrendingUp}
          trend={{ value: 5, trend: 'up' }}
          description="Team efficiency"
        />
      </div>
      {/* Add more manager-specific components here */}
    </DashboardLayout>
  );
}