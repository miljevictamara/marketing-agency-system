import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { Role } from 'src/app/infrastructure/auth/model/role.model';
import { User } from 'src/app/infrastructure/auth/model/user.model';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  user : User | undefined
  role : String | undefined
  constructor(private router: Router, private authService: AuthService){
    
  }

  ngOnInit(): void {
    this.authService.user$.subscribe(user => {
      this.user = user;
      this.hasClientRole()
    });
  }

  hasClientRole(): boolean {
   return (this.user?.roles[0]?.toString() === 'ROLE_CLIENT') 
  }

  hasEmployeeRole(): boolean {
    return (this.user?.roles[0]?.toString() === 'ROLE_EMPLOYEE') 
  }

  hasAdminRole(): boolean {
    return (this.user?.roles[0]?.toString() === 'ROLE_ADMIN') 

  }
  logout():void {
    this.authService.logout();
  }
  logIn(){
    this.router.navigate(['/login']);
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }


}
