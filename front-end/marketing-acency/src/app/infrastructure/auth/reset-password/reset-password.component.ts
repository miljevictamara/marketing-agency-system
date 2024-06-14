import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-reset-password',
  template: `
    <form (ngSubmit)="onSubmit()">
      <label for="password">New Password:</label>
      <input type="password" id="password" [(ngModel)]="password" name="password" required>
      <button type="submit">Change Password</button>
    </form>
  `
})
export class ResetPasswordComponent implements OnInit {
  password: string | undefined;
  token: string | undefined;

  constructor(private route: ActivatedRoute, private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.token = this.route.snapshot.queryParams['token'];
  }

  onSubmit() {
    this.authService.changePassword(this.token!, this.password!).subscribe(
      response => {
        console.log('Password changed successfully', response);
        alert('Password changed successfully!');
        this.router.navigate(['/login']);
      },
      error => {
        console.error('Error changing password', error);
      }
    );
  }
}
