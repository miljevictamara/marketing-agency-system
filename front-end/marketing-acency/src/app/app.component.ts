import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from './infrastructure/auth/model/user.model';
import { WebSocketService } from './infrastructure/auth/service/socket.service';
import { AuthService } from './infrastructure/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'marketing-acency';
  messages: string[] = [];
  private messageSubscription!: Subscription;
  user : User | undefined
  role : String | undefined
  
  constructor(private webSocketService: WebSocketService, private authService: AuthService,private snackBar: MatSnackBar) {}

  ngOnInit(): void {

    this.authService.user$.subscribe(user => {
      this.user = user;
    });
    if(this.user?.roles[0]?.toString() === 'ROLE_ADMIN'){
      this.messageSubscription = this.webSocketService.getMessages().subscribe((message: string) => {
        this.messages.push(message);
        this.showSnackBar(message);
      });
    }
    
  }

 

  showSnackBar(message: string): void {
    if( this.user && this.user?.roles[0]?.toString() === 'ROLE_ADMIN'){
    this.snackBar.open(message, 'Close', {
      duration: 5000, // Duration in milliseconds
      horizontalPosition: 'end', // Position horizontally, you can also use 'start' | 'center'
      verticalPosition: 'bottom' // Position vertically, you can also use 'top'
    });
  }
  }

}
