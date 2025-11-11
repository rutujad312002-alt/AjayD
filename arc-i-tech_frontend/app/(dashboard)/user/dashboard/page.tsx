import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { FileText, BookOpen, Clock, Award } from "lucide-react";

export default function UserDashboard() {
  return (
    <DashboardLayout>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Documents"
          value="25"
          icon={FileText}
          trend={{ value: 4, trend: 'up' }}
          description="Available documents"
        />
        <StatsCard
          title="Training Progress"
          value="65%"
          icon={BookOpen}
          trend={{ value: 12, trend: 'up' }}
          description="Course completion"
        />
        <StatsCard
          title="Time Spent"
          value="12h"
          icon={Clock}
          trend={{ value: 2, trend: 'up' }}
          description="This week"
        />
        <StatsCard
          title="Achievements"
          value="8"
          icon={Award}
          description="Earned badges"
        />
      </div>
      {/* Add more user specific components here */}
    </DashboardLayout>
  );
}