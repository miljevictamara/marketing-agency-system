import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Employee } from './model/employee.model';
import { Observable } from 'rxjs';

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
}
