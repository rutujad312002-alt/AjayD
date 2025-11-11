import { DashboardLayout } from "@/components/dashboard/dashboard-layout";
import { StatsCard } from "@/components/dashboard/stats-card";
import { Users, Building2, ShieldCheck, Activity, Plus, Settings, UserPlus, Lock } from "lucide-react";
import { Chart } from "@/components/dashboard/chart";
import { QuickActions } from "@/components/dashboard/quick-actions";
import { ActivityFeed } from "@/components/dashboard/activity-feed";
import { NotificationsPanel } from "@/components/dashboard/notifications-panel";

export default function SuperAdminDashboard() {
  // Sample data for the chart
  const chartData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
    datasets: [
      {
        label: 'Organizations',
        data: [65, 78, 90, 110, 123, 156],
        borderColor: 'rgb(53, 162, 235)',
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
      },
      {
        label: 'Users',
        data: [800, 1200, 1500, 1800, 2100, 2458],
        borderColor: 'rgb(255, 99, 132)',
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
      },
    ],
  };

  // Sample quick actions
  const quickActions = [
    {
      icon: Plus,
      label: 'New Organization',
      onClick: () => console.log('New Organization clicked'),
    },
    {
      icon: UserPlus,
      label: 'Add Admin',
      onClick: () => console.log('Add Admin clicked'),
    },
    {
      icon: Settings,
      label: 'System Settings',
      onClick: () => console.log('System Settings clicked'),
    },
    {
      icon: Lock,
      label: 'Security',
      onClick: () => console.log('Security clicked'),
    },
  ];

  // Sample activities
  const activities = [
    {
      id: '1',
      user: {
        name: 'John Doe',
        email: 'john@example.com',
      },
      action: 'created',
      target: 'a new organization',
      timestamp: '2 hours ago',
      status: 'success' as const,
    },
    {
      id: '2',
      user: {
        name: 'Jane Smith',
        email: 'jane@example.com',
      },
      action: 'updated',
      target: 'system settings',
      timestamp: '4 hours ago',
      status: 'success' as const,
    },
  ];

  // Sample notifications
  const notifications = [
    {
      id: '1',
      title: 'System Update',
      description: 'New security patch available',
      timestamp: '1 hour ago',
      read: false,
      type: 'warning' as const,
    },
    {
      id: '2',
      title: 'New Organization',
      description: 'TechCorp has joined the platform',
      timestamp: '2 hours ago',
      read: true,
      type: 'success' as const,
    },
  ];

  return (
    <DashboardLayout>
      <div className="space-y-6">
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          <StatsCard
            title="Total Organizations"
            value="156"
            icon={Building2}
            trend={{ value: 15, trend: 'up' }}
            description="Registered organizations"
          />
          <StatsCard
            title="Active Users"
            value="2,458"
            icon={Users}
            trend={{ value: 12, trend: 'up' }}
            description="Across all organizations"
          />
          <StatsCard
            title="System Health"
            value="99.9%"
            icon={Activity}
            trend={{ value: 0.5, trend: 'up' }}
            description="Platform uptime"
          />
          <StatsCard
            title="Security Status"
            value="Protected"
            icon={ShieldCheck}
            description="All systems secured"
          />
        </div>

        <div className="grid gap-6 md:grid-cols-2">
          <Chart
            type="line"
            data={chartData}
            title="Growth Overview"
            description="Organizations and users over time"
          />
          <NotificationsPanel
            notifications={notifications}
            onNotificationClick={(n) => console.log('Notification clicked:', n)}
            onClearAll={() => console.log('Clear all notifications')}
          />
        </div>

        <QuickActions actions={quickActions} />

        <div className="grid gap-6 md:grid-cols-2">
          <ActivityFeed
            activities={activities}
            className="md:col-span-2"
          />
        </div>
      </div>
    </DashboardLayout>
  );
}