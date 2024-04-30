import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/infrastructure/auth/model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser!:any;
  private baseUrl = 'https://localhost:8443';

  constructor(private http: HttpClient) { }

  getUserByToken(tokenId: string, hmac: string): Observable<User> {
    const encodedHmac = encodeURIComponent(hmac);
    return this.http.get<User>('https://localhost:8443/activation/' + tokenId + "/" + encodedHmac);
  }


  updateIsActivated(userId: number): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/user/activation/${userId}`, {});
  }
}
