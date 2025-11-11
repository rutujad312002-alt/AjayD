import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { Code, GitBranch, Clock, Target } from "lucide-react";

export default function DeveloperDashboard() {
  return (
    <DashboardLayout>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Code Commits"
          value="156"
          icon={GitBranch}
          trend={{ value: 23, trend: 'up' }}
          description="This sprint"
        />
        <StatsCard
          title="Active Features"
          value="12"
          icon={Code}
          trend={{ value: 4, trend: 'up' }}
          description="In development"
        />
        <StatsCard
          title="Sprint Hours"
          value="87"
          icon={Clock}
          trend={{ value: 15, trend: 'up' }}
          description="Hours logged"
        />
        <StatsCard
          title="Sprint Progress"
          value="78%"
          icon={Target}
          trend={{ value: 8, trend: 'up' }}
          description="Sprint completion"
        />
      </div>
      {/* Add more developer specific components here */}
    </DashboardLayout>
  );
}