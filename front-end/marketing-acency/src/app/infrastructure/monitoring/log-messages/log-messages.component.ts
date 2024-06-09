// src/app/components/log-messages/log-messages.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../../auth/service/socket.service';
import { User } from '../../auth/model/user.model';
import { AuthService } from '../../auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-log-messages',
  templateUrl: './log-messages.component.html',
  styleUrls: ['./log-messages.component.css']
})
export class LogMessagesComponent implements OnInit, OnDestroy {
  messages: string[] = [];
  private messageSubscription!: Subscription;
  user : User | undefined
  role : String | undefined
  
  constructor(private webSocketService: WebSocketService, private authService: AuthService,private snackBar: MatSnackBar) {}

  ngOnInit(): void {

    this.authService.user$.subscribe(user => {
      this.user = user;
    });
    this.messageSubscription = this.webSocketService.getMessages().subscribe((message: string) => {
      this.messages.push(message);
      this.showSnackBar(message);
    });
  }

  hasAdminRole(): boolean {
    return (this.user?.roles[0]?.toString() === 'ROLE_ADMIN') }

  ngOnDestroy(): void {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
  }
  showSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 5000, // Duration in milliseconds
      horizontalPosition: 'end', // Position horizontally, you can also use 'start' | 'center'
      verticalPosition: 'bottom' // Position vertically, you can also use 'top'
    });
  }


// src/app/components/log-messages/log-messages.component.ts
getMessageDate(message: string): string {
  const dateMatch = message.match(/\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}\+\d{2}:\d{2}/);
  return dateMatch ? dateMatch[0] : '';
}

getMessageType(message: string): string {
  const typeMatch = message.match(/\|\s(\w+)\s\|/);
  return typeMatch ? typeMatch[1] : 'INFO';
}

getMessageContent(message: string): string {
  const contentMatch = message.match(/\|\s\[.+\]\s\|\s(.+)/);
  return contentMatch ? contentMatch[1] : message;
}

}
