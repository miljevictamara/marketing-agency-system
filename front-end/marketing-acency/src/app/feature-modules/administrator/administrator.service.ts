import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Administrator } from './model/administrator.model';
import { User } from 'src/app/infrastructure/auth/model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {

  private baseUrl = 'https://localhost:8443';

  constructor(private http: HttpClient) { }

  getAdministratorByUserId(userId: number): Observable<Administrator> {
    return this.http.get<Administrator>('https://localhost:8443/administrator/byUserId/' + userId);
  }

  updateAdministrator(administrator: Administrator): Observable<Administrator> {
    return this.http.put<Administrator>('https://localhost:8443/administrator/update', administrator);
  }

  createUser(user: User): Observable<any> {
    return this.http.post<any>('https://localhost:8443/client/save-user-simpler', user);
  }
  

  createAdministrator(employee: Administrator): Observable<Administrator> {
    return this.http.post<Administrator>('https://localhost:8443/administrator/create', employee);
  }
}
