import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { User } from '../auth/model/user.model';
import { Role } from '../auth/model/role.model';
import { switchMap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  loggedInUser: User | null = null;
  user: User | undefined;

  constructor(private router: Router, private authService: AuthService, private http: HttpClient) {}

  checkCommonPermissions(roleName: string, loggedInRole: string): Observable<boolean> {
    return this.http.get<boolean>(`https://localhost:8443/permissions/check/${roleName}/${loggedInRole}`);
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authService.user$.pipe(
      switchMap(user => {
        if (user) {
          this.loggedInUser = user;
          const roleName = route.data['role'][0];
          return this.checkCommonPermissions(roleName, this.loggedInUser.roles[0].toString()).pipe(
            switchMap(isTrue => {
              const move = isTrue;
              if (
                (route.routeConfig?.path === 'administrator-profile' ||
                route.routeConfig?.path === 'client-profile' ||
                route.routeConfig?.path === 'permission-page' ||
                route.routeConfig?.path === 'employee-profile' ||
                route.routeConfig?.path === 'employee-form' ||
                route.routeConfig?.path === 'administrator-form') &&
                move
              ) {
                return [true];
              } else {
                // Redirect to home if permission is not granted
                this.router.navigate(['/']);
                return [false];
              }
            })
          );
        } else {
          // Redirect to home if user is not logged in
          this.router.navigate(['/']);
          return [false];
        }
      })
    );
  }
}
