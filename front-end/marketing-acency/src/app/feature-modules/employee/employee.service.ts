import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from './model/employee.model';
import { Observable } from 'rxjs';
import { User } from 'src/app/infrastructure/auth/model/user.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'https://localhost:8443';

  constructor(private http: HttpClient) { }

  getEmployeeByUserId(userId: number): Observable<Employee> {
    return this.http.get<Employee>('https://localhost:8443/employee/byUserId/' + userId);
  }

  updateEmployee(employee: Employee): Observable<Employee> {
    return this.http.put<Employee>('https://localhost:8443/employee/update', employee);
  }

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>('https://localhost:8443/employee/all/');
  }

  createUser(user: User): Observable<any> {
    return this.http.post<any>('https://localhost:8443/client/save-user-simpler', user);
  }
  

  createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>('https://localhost:8443/employee/create', employee);
  }
}
