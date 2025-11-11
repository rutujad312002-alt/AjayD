'use client';

import { BellRing, Check } from "lucide-react";
import { cn } from "@/lib/utils";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

export interface Notification {
  id: string;
  title: string;
  description: string;
  timestamp: string;
  read: boolean;
  type?: 'info' | 'warning' | 'error' | 'success';
}

interface NotificationsPanelProps {
  notifications: Notification[];
  className?: string;
  onNotificationClick?: (notification: Notification) => void;
  onClearAll?: () => void;
}

export function NotificationsPanel({
  notifications,
  className,
  onNotificationClick,
  onClearAll,
}: NotificationsPanelProps) {
  const unreadCount = notifications.filter(n => !n.read).length;

  return (
    <Card className={cn("", className)}>
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
        <CardTitle className="text-sm font-medium">
          Notifications
        </CardTitle>
        <div className="flex items-center space-x-2">
          {unreadCount > 0 && (
            <span className="relative flex h-2 w-2">
              <span className="absolute inline-flex h-full w-full animate-ping rounded-full bg-sky-400 opacity-75"></span>
              <span className="relative inline-flex h-2 w-2 rounded-full bg-sky-500"></span>
            </span>
          )}
          <BellRing className="h-4 w-4 text-muted-foreground" />
        </div>
      </CardHeader>
      <CardDescription className="text-xs">
        You have {unreadCount} unread messages
      </CardDescription>
      <CardContent className="mt-4 grid gap-4">
        {notifications.map((notification) => (
          <div
            key={notification.id}
            className={cn(
              "flex items-start space-x-4 rounded-md p-2 transition-all hover:bg-accent",
              !notification.read && "bg-muted",
              "cursor-pointer"
            )}
            onClick={() => onNotificationClick?.(notification)}
          >
            <div className="flex-1 space-y-1">
              <p className="text-sm font-medium leading-none">
                {notification.title}
                {notification.read && (
                  <Check className="float-right h-4 w-4 text-green-500" />
                )}
              </p>
              <p className="text-sm text-muted-foreground">
                {notification.description}
              </p>
              <p className="text-xs text-muted-foreground">
                {notification.timestamp}
              </p>
            </div>
          </div>
        ))}
        {notifications.length > 0 && (
          <Button
            variant="outline"
            className="w-full"
            onClick={onClearAll}
          >
            Clear all
          </Button>
        )}
      </CardContent>
    </Card>
  );
}