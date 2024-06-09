import { Injectable } from '@angular/core';
import { Client, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client;
  private messageSubject: Subject<string> = new Subject<string>();

  constructor(private authService: AuthService) { 
    this.stompClient = Stomp.over(() => new SockJS('https://localhost:8443/ws'));
    this.stompClient.reconnectDelay = 15000;
    this.stompClient.debug = (str) => console.log(str);

    this.stompClient.onConnect = () => {
      const headers = {
        Authorization: 'Bearer ' + this.authService.getJwtToken() 
      };
      this.stompClient.subscribe('/topic/logs', (message) => {
        this.messageSubject.next(message.body);
      }, headers); // Pass the headers to subscribe method
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    this.stompClient.activate();
  }

  public getMessages(): Observable<string> {
    return this.messageSubject.asObservable();
  }
}
