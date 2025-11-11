'use client';

import { Button } from '@/components/ui/button';
import { useRouter } from 'next/navigation';
import { useAuthStore } from '@/store/auth';

export default function UnauthorizedPage() {
  const router = useRouter();
  const { user, clearAuth } = useAuthStore();

  const handleLogout = () => {
    clearAuth();
    router.push('/login');
  };

  const handleBack = () => {
    if (user) {
      const roleRoutes: Record<string, string> = {
        SUPER_ADMIN: '/super-admin/dashboard',
        SUB_ADMIN: '/sub-admin/dashboard',
        PROJECT_DEVELOPER_TEAM: '/developer/dashboard',
        TEAM_MEMBER: '/team-member/dashboard',
        SOFTWARE_USER: '/user/dashboard',
        TRAINING_USER: '/user/dashboard',
        USER: '/user/dashboard',
      };
      router.push(roleRoutes[user.role] || '/');
    } else {
      router.push('/login');
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-background">
      <div className="max-w-md text-center space-y-6 p-6">
        <h1 className="text-4xl font-bold text-destructive">Access Denied</h1>
        <p className="text-lg text-muted-foreground">
          You don't have permission to access this page.
        </p>
        <div className="space-x-4">
          <Button onClick={handleBack}>Go Back</Button>
          <Button variant="outline" onClick={handleLogout}>
            Logout
          </Button>
        </div>
      </div>
    </div>
  );
}