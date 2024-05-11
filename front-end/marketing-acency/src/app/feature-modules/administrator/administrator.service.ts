import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Administrator } from './model/administrator.model';

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
}
