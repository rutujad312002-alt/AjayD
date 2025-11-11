'use client';

import { usePathname } from 'next/navigation';
import Link from 'next/link';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import {
  ChevronLeft,
  ChevronRight,
  LayoutDashboard,
  Users,
  FolderKanban,
  GraduationCap,
  Settings,
  LogOut,
} from 'lucide-react';

interface SidebarProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
}

const menuItems = {
  'super-admin': [
    { name: 'Dashboard', href: '/super-admin/dashboard', icon: LayoutDashboard },
    { name: 'Users Management', href: '/super-admin/users', icon: Users },
    { name: 'Teams', href: '/super-admin/teams', icon: FolderKanban },
    { name: 'Services', href: '/super-admin/services', icon: Settings },
  ],
  'sub-admin': [
    { name: 'Dashboard', href: '/sub-admin/dashboard', icon: LayoutDashboard },
    { name: 'Teams', href: '/sub-admin/teams', icon: FolderKanban },
    { name: 'Projects', href: '/sub-admin/projects', icon: FolderKanban },
    { name: 'Training', href: '/sub-admin/training', icon: GraduationCap },
  ],
  'project-developer': [
    { name: 'Dashboard', href: '/developer/dashboard', icon: LayoutDashboard },
    { name: 'Projects', href: '/developer/projects', icon: FolderKanban },
    { name: 'Tasks', href: '/developer/tasks', icon: FolderKanban },
  ],
  'team-member': [
    { name: 'Dashboard', href: '/team-member/dashboard', icon: LayoutDashboard },
    { name: 'Students', href: '/team-member/students', icon: Users },
    { name: 'Sessions', href: '/team-member/sessions', icon: GraduationCap },
  ],
  user: [
    { name: 'Dashboard', href: '/user/dashboard', icon: LayoutDashboard },
    { name: 'Projects', href: '/user/projects', icon: FolderKanban },
    { name: 'Training', href: '/user/training', icon: GraduationCap },
  ],
};

export default function Sidebar({ open, onOpenChange }: SidebarProps) {
  const pathname = usePathname();
  // TODO: Get user role from auth store
  const userRole = 'super-admin';
  const items = menuItems[userRole as keyof typeof menuItems];

  return (
    <>
      {/* Mobile backdrop */}
      {open && (
        <div
          className="fixed inset-0 z-40 bg-black/20 lg:hidden"
          onClick={() => onOpenChange(false)}
        />
      )}

      {/* Sidebar */}
      <div
        className={cn(
          'fixed top-0 bottom-0 left-0 z-50 w-64 bg-card border-r transition-transform duration-300 lg:translate-x-0',
          open ? 'translate-x-0' : '-translate-x-full'
        )}
      >
        {/* Logo */}
        <div className="h-16 flex items-center justify-between px-4 border-b">
          <Link href="/dashboard" className="flex items-center space-x-2">
            <span className="text-xl font-bold">Arc-i-Tech</span>
          </Link>
          <Button
            variant="ghost"
            size="icon"
            onClick={() => onOpenChange(!open)}
            className="lg:hidden"
          >
            <ChevronLeft className="h-4 w-4" />
          </Button>
        </div>

        {/* Navigation */}
        <nav className="space-y-1 px-2 py-4">
          {items.map((item) => {
            const isActive = pathname.startsWith(item.href);
            return (
              <Link
                key={item.href}
                href={item.href}
                className={cn(
                  'flex items-center space-x-2 px-3 py-2 rounded-md text-sm font-medium transition-colors',
                  isActive
                    ? 'bg-primary/10 text-primary'
                    : 'hover:bg-accent hover:text-accent-foreground'
                )}
              >
                <item.icon className="h-5 w-5" />
                <span>{item.name}</span>
              </Link>
            );
          })}
        </nav>

        {/* Bottom section */}
        <div className="absolute bottom-0 left-0 right-0 p-4 border-t">
          <Button
            variant="ghost"
            className="w-full justify-start space-x-2"
            onClick={() => {
              // TODO: Implement logout
            }}
          >
            <LogOut className="h-5 w-5" />
            <span>Logout</span>
          </Button>
        </div>
      </div>

      {/* Collapse button (desktop only) */}
      <Button
        variant="ghost"
        size="icon"
        onClick={() => onOpenChange(!open)}
        className={cn(
          'fixed left-64 top-4 z-50 hidden lg:flex',
          open ? 'translate-x-0' : '-translate-x-44'
        )}
      >
        {open ? (
          <ChevronLeft className="h-4 w-4" />
        ) : (
          <ChevronRight className="h-4 w-4" />
        )}
      </Button>
    </>
  );
}
