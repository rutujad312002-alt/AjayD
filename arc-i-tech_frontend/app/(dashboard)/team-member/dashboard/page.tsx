import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { Users, ClipboardList, Clock, Target } from "lucide-react";

export default function TeamMemberDashboard() {
  return (
    <DashboardLayout>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Team Size"
          value="8"
          icon={Users}
          description="Current team members"
        />
        <StatsCard
          title="My Tasks"
          value="15"
          icon={ClipboardList}
          trend={{ value: 5, trend: 'up' }}
          description="Active tasks"
        />
        <StatsCard
          title="Work Hours"
          value="32"
          icon={Clock}
          trend={{ value: 2, trend: 'up' }}
          description="This week"
        />
        <StatsCard
          title="Task Progress"
          value="85%"
          icon={Target}
          trend={{ value: 10, trend: 'up' }}
          description="Overall completion"
        />
      </div>
      {/* Add more team member specific components here */}
    </DashboardLayout>
  );
}