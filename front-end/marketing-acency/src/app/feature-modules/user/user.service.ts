import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/infrastructure/auth/model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser!:any;
  private baseUrl = 'https://localhost:8443/';

  constructor(private http: HttpClient) { }

  /*getMyInfo(mail: string): void {
    this.http.get<User>(this.baseUrl + 'user/getByEmail/' + mail)
      .subscribe(
        user => {
          console.log('Received User:', user);        
          this.currentUser = user;
          localStorage.setItem('user',JSON.stringify(this.currentUser));
        },
        error => {
          console.error('Error fetching user info:', error);
        }
      );
  }

  getUserDetails(mail: string): Observable<User> {
    return this.http.get<User>(this.baseUrl + `user/getByEmail/${mail}`);
  }*/
}
