import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, catchError, throwError, map } from 'rxjs';
import { User } from './model/user.model';
import { ApiService } from './service/api.service';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user$ = new BehaviorSubject<User>({id: 0, mail: "", password: "", confirmationPassword: "", isBlocked: false, isActivated:false});
  //user$ = new BehaviorSubject<User>({email: "", id: 0 });
  private access_token: string | null = null; 
  private refresh_token: string | null = null; 
  isFirstLogin: boolean = false;
  clearTimeout: any;

  constructor(private http: HttpClient,
    private router: Router,
    private apiService: ApiService,
    private userService: UserService) { 
      const storedToken = localStorage.getItem('access_token');
      if (storedToken) {
        this.access_token = storedToken;
        this.setUser();
      }
    }


  findByMail(mail: string): Observable<any> {
    return this.http.get(`https://localhost:8443/user/findByMail/${mail}`);
  }  

  createUser(user: User): Observable<any> {   
    return this.http.post('https://localhost:8443/client/register', user)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          console.error(error);
          return throwError("Failed to register user");
        })
      );
  }

  
  login(user: User) {

    const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    // const body = `username=${user.username}&password=${user.password}`;
    const body = {
      'mail': user.mail,
      'password': user.password
    };

  
    
    
    return this.apiService.post(`https://localhost:8443/auth/login`, JSON.stringify(body), loginHeaders)
      .pipe(map((res) => {
        console.log('Login success',res);
        this.access_token = res.body.accessToken;
        this.refresh_token = res.body.refreshToken;
        localStorage.setItem("access_token", res.body.accessToken)
        localStorage.setItem("refresh_token", res.body.refreshToken)
        this.autoLogout(res.body.accessExpiresIn)
        //this.userService.getMyInfo(user.mail);
        this.setUser();
        return res;
      }));
  }

  checkIfUserExists(): void {
    const accessToken = localStorage.getItem('access_token');
    if (accessToken == null) {
      return;
    }
    this.setUser();
  }

  private setUser(): void {
    const jwtHelperService = new JwtHelperService();
    const accessToken = this.getToken() || ""; 

    const decodedToken = jwtHelperService.decodeToken(accessToken);
    const user: User = {
      id: decodedToken.id,
      mail: decodedToken.sub,
      password: decodedToken.name || '',   
      confirmationPassword: decodedToken.confirmationPassword || '',
      isBlocked: decodedToken.isBlocked || '',
      isActivated: decodedToken.isActivated || ''
    };
  
    this.user$.next(user);
  }

  autoLogout(expirationDate: number) {
    this.clearTimeout = setTimeout(() => {
      this.logout();
    }, expirationDate)
  }
  

  logout() {
    this.userService.currentUser = null;
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");

    if (this.clearTimeout){
      clearTimeout (this.clearTimeout);
    }

    this.access_token = null;
    this.router.navigate(['/login']);
    this.user$.next({id: 0, mail: "", password: "", confirmationPassword: "", isBlocked: false, isActivated:false })
  }

  tokenIsPresent() {
    return this.access_token != undefined && this.access_token != null;
  }

  getToken() {
    return this.access_token;
  }
}
