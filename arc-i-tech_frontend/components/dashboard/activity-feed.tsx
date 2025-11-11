'use client';

import { ScrollArea } from "@/components/ui/scroll-area";
import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";
import { cn } from "@/lib/utils";

export interface Activity {
  id: string;
  user: {
    name: string;
    avatar?: string;
    email: string;
  };
  action: string;
  target: string;
  timestamp: string;
  status?: 'success' | 'pending' | 'error';
}

interface ActivityFeedProps {
  activities: Activity[];
  className?: string;
}

export function ActivityFeed({ activities, className }: ActivityFeedProps) {
  return (
    <ScrollArea className={cn("h-[400px] rounded-md border p-4", className)}>
      <div className="space-y-4">
        {activities.map((activity) => (
          <div
            key={activity.id}
            className="flex items-start gap-4 rounded-lg p-3 transition-colors hover:bg-muted/50"
          >
            <Avatar className="h-8 w-8">
              <AvatarImage src={activity.user.avatar} alt={activity.user.name} />
              <AvatarFallback>
                {activity.user.name.split(' ').map(n => n[0]).join('')}
              </AvatarFallback>
            </Avatar>
            <div className="flex-1 space-y-1">
              <p className="text-sm font-medium leading-none">
                {activity.user.name}
                <span className="text-muted-foreground"> {activity.action} </span>
                {activity.target}
              </p>
              <p className="text-xs text-muted-foreground">
                {activity.timestamp}
              </p>
            </div>
            {activity.status && (
              <div className={cn(
                "text-xs font-medium",
                activity.status === 'success' && "text-green-500",
                activity.status === 'pending' && "text-yellow-500",
                activity.status === 'error' && "text-red-500"
              )}>
                {activity.status.charAt(0).toUpperCase() + activity.status.slice(1)}
              </div>
            )}
          </div>
        ))}
      </div>
    </ScrollArea>
  );
}