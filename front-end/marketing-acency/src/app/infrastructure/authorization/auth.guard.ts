import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { User } from '../auth/model/user.model';
import { Role } from '../auth/model/role.model';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  loggedInUser: User | null = null;
  user : User | undefined

  constructor( private router:Router, private authService: AuthService){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        this.authService.user$.subscribe(user => {
          this.user = user;
        });
          if (this.user) {
            this.loggedInUser = this.user;
            if (
              route.routeConfig?.path === 'administrator-form' &&
              this.user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            }
            if (
              route.routeConfig?.path === 'employee-form' &&
              this.user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            }
            if (
              route.routeConfig?.path === 'administrator-profile' &&
              this.user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            }
            if (
              route.routeConfig?.path === 'client-profile' &&
              this.user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            } if (
              route.routeConfig?.path === 'employee-profile' &&
              this.user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            } 
            if (
              route.routeConfig?.path === 'permission-page' &&
              this.user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            }else {
              this.router.navigate(['/']);
              return false;
            }
          } else {
            this.router.navigate(['/']);
            return false;
          }
        }
    
  
  
  
}