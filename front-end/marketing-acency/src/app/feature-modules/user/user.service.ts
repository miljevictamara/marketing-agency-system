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
  private baseUrl = 'https://localhost:8443/';

  constructor(private http: HttpClient) { }

  getPackageByName(name: string): Observable<Package> {
    return this.http.get<Package>(`https://localhost:8443/package/${name}`);
  }

  findUserIdByEmail(mail: string): Observable<User> {
    return this.http.get<User>(`https://localhost:8443/auth/findByEmail/${mail}`);
  }

}
