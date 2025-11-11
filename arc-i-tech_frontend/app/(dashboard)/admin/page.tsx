import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { Users, Building2, ShieldCheck, Activity } from "lucide-react";

export default function AdminDashboard() {
  return (
    <DashboardLayout>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Total Users"
          value="1,234"
          icon={Users}
          trend={{ value: 12, trend: 'up' }}
          description="Total registered users"
        />
        <StatsCard
          title="Active Projects"
          value="45"
          icon={Building2}
          trend={{ value: 8, trend: 'up' }}
          description="Projects in progress"
        />
        <StatsCard
          title="System Health"
          value="98.5%"
          icon={Activity}
          trend={{ value: 2, trend: 'up' }}
          description="Overall system uptime"
        />
        <StatsCard
          title="Security Status"
          value="Secure"
          icon={ShieldCheck}
          description="All systems operational"
        />
      </div>
      {/* Add more admin-specific components here */}
    </DashboardLayout>
  );
}