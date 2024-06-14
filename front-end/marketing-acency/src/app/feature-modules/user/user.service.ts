import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/infrastructure/auth/model/user.model';
import { Package } from './model/package.model';
import { Client } from './model/client.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser!:any;
  private baseUrl = 'https://localhost:8443';

  constructor(private http: HttpClient) { }

  getUserByToken(tokenId: string, hmac: string): Observable<User> {
    return this.http.get<User>('https://localhost:8443/activation/' + tokenId + '/' + hmac);
  }


  updateIsActivated(userId: number): Observable<any>{
    return this.http.put<any>('https://localhost:8443/user/activation/' + userId, {});
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

  findByUserId(userId: number): Observable<User> {
    return this.http.get<User>(`https://localhost:8443/auth/findByUserId/${userId}`);
  }

  getAllIndividuals(): Observable<Client[]> {
    return this.http.get<Client[]>(`https://localhost:8443/auth/allIndividuals`);
  }

  getAllLegalEntities(): Observable<Client[]> {
    return this.http.get<Client[]>(`https://localhost:8443/auth/allLegalEntities`);
  }

  approveRegistration(userId: number): Observable<any>{
    const url = `https://localhost:8443/client/approve-registration-request/${userId}`;
    return this.http.put(url, {});
  }

  rejectRegistration(userId: number, reason: string): Observable<any>{
    return this.http.put<any>(`https://localhost:8443/client/reject-registration-request/${userId}/${reason}`, {});
  }

  updateIsBlocked(userId: number): Observable<any>{
    return this.http.put<any>('https://localhost:8443/user/blocking/' + userId, {});
  }
  

}
