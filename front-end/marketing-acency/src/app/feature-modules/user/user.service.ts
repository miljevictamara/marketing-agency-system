import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/infrastructure/auth/model/user.model';
import { Package } from './model/package.model';

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


  getUserByLoginToken(tokenId: string): Observable<User> {
    return this.http.get<User>('https://localhost:8443/auth/' + tokenId);
  }

  getUserByMail(mail: string): Observable<User> {
    return this.http.get<User>('https://localhost:8443/user/' + mail);
  }

  checkIfUserHasAppropriatePackage(mail: string): Observable<boolean> {
    return this.http.get<boolean>('https://localhost:8443/user/package/' + mail);
  }

  
  getPackageByName(name: string): Observable<Package> {
    return this.http.get<Package>(`https://localhost:8443/package/${name}`);
  }

  findUserIdByEmail(mail: string): Observable<User> {
    return this.http.get<User>(`https://localhost:8443/auth/findByEmail/${mail}`);
  }


}
