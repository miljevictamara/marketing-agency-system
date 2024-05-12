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

  constructor( private router:Router, private authService: AuthService){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isUserLoggedIn()) {
      return this.authService.getLoggedInUser().pipe(
        map((user: User | null) => {
          if (user) {
            this.loggedInUser = user;
            if (
              route.routeConfig?.path === 'activation/:tokenId' &&
              user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            } else if (
              route.routeConfig?.path === 'employee-profile' &&
              user.roles.some((role: Role) => route.data['role'][0]===this.loggedInUser?.roles[0])
            ) {
              return true;
            } else {
              this.router.navigate(['/']);
              return false;
            }
          } else {
            this.router.navigate(['/']);
            return false;
          }
        })
      );
    } else {
      this.router.navigate(['/']);
      return false;
    }
  }
  
  
}