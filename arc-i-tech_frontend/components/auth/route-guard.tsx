'use client';

import { useEffect } from 'react';
import { useRouter, usePathname } from 'next/navigation';
import { useAuthStore } from '@/store/auth';

const roleBasedRoutes = {
  'SUPER_ADMIN': ['/super-admin'],
  'SUB_ADMIN': ['/sub-admin'],
  'PROJECT_DEVELOPER_TEAM': ['/developer'],
  'TEAM_MEMBER': ['/team-member'],
  'SOFTWARE_USER': ['/user'],
  'TRAINING_USER': ['/user'],
};

const publicRoutes = ['/login', '/register', '/forgot-password'];

export default function RouteGuard({ children }: { children: React.ReactNode }) {
  const router = useRouter();
  const pathname = usePathname();
  const { isAuthenticated, user } = useAuthStore();

  useEffect(() => {
    if (publicRoutes.includes(pathname)) {
      if (isAuthenticated) {
        // Redirect authenticated users to their dashboard
        const role = user?.role || 'USER';
        const basePath = roleBasedRoutes[role as keyof typeof roleBasedRoutes]?.[0] || '/';
        router.push(`${basePath}/dashboard`);
      }
      return;
    }

    if (!isAuthenticated) {
      // Redirect unauthenticated users to login
      router.push('/login');
      return;
    }

    if (user) {
      const role = user.role;
      const allowedPaths = roleBasedRoutes[role as keyof typeof roleBasedRoutes] || [];
      
      // Check if current path starts with any of the allowed paths
      const hasAccess = allowedPaths.some(path => pathname.startsWith(path));
      
      if (!hasAccess) {
        // Redirect to default dashboard for role
        router.push(`${allowedPaths[0]}/dashboard`);
      }
    }
  }, [pathname, isAuthenticated, user, router]);

  return <>{children}</>;
}