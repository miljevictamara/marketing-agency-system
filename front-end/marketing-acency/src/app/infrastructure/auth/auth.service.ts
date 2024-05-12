import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, catchError, throwError, map } from 'rxjs';
import { User } from './model/user.model';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ApiService } from './service/api.service';
import { Client } from 'src/app/feature-modules/user/model/client.model';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user$ = new BehaviorSubject<User>({id: 0, mail: "", password: "", confirmationPassword: "", roles: [{ id: 0, name: '', permissions: [] }], isBlocked: false, isActivated:false});
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
    return this.http.get(`https://localhost:8443/auth/findByEmail/${mail}`);
  }  


  saveUser(user: User): Observable<any> { 
    return this.http.post<User>(`https://localhost:8443/client/save-user`, user);
  }

  registerClient(client: Client): Observable<any> {
    return this.http.post<Client>(`https://localhost:8443/client/register`, client);
  }
  

  
  login(user: User) {

    if (user.isBlocked) {
        console.log('Korisnik je blokiran ili neaktivan. Nije moguće izvršiti logovanje.');
        return throwError('Korisnik je blokiran. Nije moguće izvršiti logovanje.');
      }

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
        this.autoLogout(res.body.refreshExpiresIn) //KADA ISTEKNE REFRESHTOKEN -> AUTOLOGOUT
        this.AccessTokenExpired(res.body.accessExpiresIn)
        //this.AccessTokenExpired(40000)
        //this.autoLogout(100000) //KADA ISTEKNE REFRESHTOKEN -> AUTOLOGOUT
        //this.userService.getMyInfo(user.mail);
        this.setUser();
        return res;
      }
    ));
  }


  AccessTokenExpired(expirationDate: number) {
    this.clearTimeout = setTimeout(() => {
      this.generateNewAccessToken();
    }, expirationDate)
  }

  passwordlessLogin(user: User) {
    this.getTokens(user.mail).subscribe({
        next: (res: any) => {
            console.log('Response:', res); 
            this.access_token = res.accessToken;
            this.refresh_token = res.refreshToken;
            localStorage.setItem("access_token", res.accessToken);
            localStorage.setItem("refresh_token", res.refreshToken);
            this.autoLogout(res.accessExpiresIn);
            this.setUser();
        },
        error: (error: any) => {
            console.error('Error:', error); 
        }
    });
}

getTokens(mail: string) {
    return this.http.post<any>(`https://localhost:8443/auth/login-tokens`,mail);
}

  

  sendMail(mail: string) {
    return this.http.post('https://localhost:8443/auth/passwordless-login',  mail );
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
      roles: decodedToken.roles || '',     
      isBlocked: decodedToken.isBlocked || '',
      isActivated: decodedToken.isActivated || ''
    };
  
    this.user$.next(user);
  }
  getLoggedInUser(): Observable<User> {
    return this.user$.asObservable();
  }

  isUserLoggedIn(): Observable<boolean> {
    return this.getLoggedInUser().pipe(
      map(user => {
        if (!user) {
          return false; 
        }
        return user.roles.some(role => role.name === 'ROLE_ADMIN' || role.name === 'ROLE_EMPLOYEE' || role.name === 'ROLE_CLIENT');
      })
    );
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
      return;
    }
  
    const jwtHelperService = new JwtHelperService();
    const decodedToken = jwtHelperService.decodeToken(refreshToken);
    const mail = decodedToken.sub;
  
    const requestBody = mail;
  
    this.apiService.post(`https://localhost:8443/auth/refreshToken`, requestBody).subscribe((res: any) => {
      //if (res.body && res.body.accessToken) {
     //   const decodedAccessToken = jwtHelperService.decodeToken(res.body.accessToken);
      //  if (decodedAccessToken && decodedAccessToken.isBlocked) {
       //   this.logout();
        //  console.log("Korisnik je blokiran. Izvršen je logout.");
        //  return;
       // }
        this.access_token = res.body.accessToken;
        localStorage.setItem("access_token", res.body.accessToken);
        this.AccessTokenExpired(res.body.accessExpiresIn);
        console.log("Kreiran access token", this.access_token);
      //} else {
     // }
    }, error => {
      console.error("Greška prilikom dohvatanja novog tokena:", error);
      this.logout();
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
    this.user$.next({id: 0, mail: "", password: "", confirmationPassword: "", roles: [{ id: 0, name: '', permissions: [] }], isBlocked: false, isActivated:false })
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
