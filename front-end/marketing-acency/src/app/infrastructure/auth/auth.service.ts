import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, catchError, throwError, map } from 'rxjs';
import { User } from './model/user.model';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ApiService } from './service/api.service';


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
        //this.autoLogout(res.body.refreshExpiresIn) //KADA ISTEKNE REFRESHTOKEN -> AUTOLOGOUT
        //this.AccessTokenExpired(res.body.accessExpiresIn)
        this.autoLogout(100000) //KADA ISTEKNE REFRESHTOKEN -> AUTOLOGOUT
        this.AccessTokenExpired(6000)
        //this.userService.getMyInfo(user.mail);
        this.setUser();
        return res;
      }));
  }


  AccessTokenExpired(expirationDate: number) {
    this.clearTimeout = setTimeout(() => {
      this.generateNewAccessToken();
    }, expirationDate)
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
  
generateNewAccessToken() {
  console.log("Pokrenuta provera za kreiranje novog access tokena");
  const refreshToken = localStorage.getItem("refresh_token");
  localStorage.removeItem("access_token");

  if (!refreshToken) {
    this.logout();
    return undefined;
  }

  const jwtHelperService = new JwtHelperService();
  const decodedToken = jwtHelperService.decodeToken(refreshToken);
  const mail = decodedToken.sub;

  const requestBody = mail;

  // Prosledite instancu HttpHeaders u zahtev
  this.apiService.post(`https://localhost:8443/auth/refreshToken`, requestBody).subscribe((res: any) => {
    this.access_token = res.body.accessToken;
    localStorage.setItem("access_token", res.body.accessToken);
    this.AccessTokenExpired(6000);
    console.log("Kreiran access tokena", this.access_token);
  });
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

  getCurrentUserId(): number | undefined {
    const accessToken = this.getToken();
    if (!accessToken) return undefined;

    const jwtHelperService = new JwtHelperService();
    const decodedToken = jwtHelperService.decodeToken(accessToken);

    // Extract user ID from decoded JWT token
    const userId = decodedToken && decodedToken.id;

    return userId ? +userId : undefined; // Convert to number or return undefined
  }
}
