import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']

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
