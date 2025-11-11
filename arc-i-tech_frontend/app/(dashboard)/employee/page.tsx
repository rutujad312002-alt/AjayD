import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { CheckCircle, Clock, Calendar, Target } from "lucide-react";

export default function EmployeeDashboard() {
  return (
    <DashboardLayout>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Tasks Completed"
          value="12"
          icon={CheckCircle}
          trend={{ value: 8, trend: 'up' }}
          description="Tasks completed this week"
        />
        <StatsCard
          title="Work Hours"
          value="38"
          icon={Clock}
          trend={{ value: 2, trend: 'up' }}
          description="Hours this week"
        />
        <StatsCard
          title="Upcoming Tasks"
          value="5"
          icon={Calendar}
          description="Due this week"
        />
        <StatsCard
          title="Goals Progress"
          value="85%"
          icon={Target}
          trend={{ value: 10, trend: 'up' }}
          description="Quarterly goals"
        />
      </div>
      {/* Add more employee-specific components here */}
    </DashboardLayout>
  );
}