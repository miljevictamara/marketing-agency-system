import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-reset-password-request',
  templateUrl: './reset-password-request.component.html',
  styleUrls: ['./reset-password-request.component.css']
})
export class ResetPasswordRequestComponent {
  email: string | undefined;

  constructor(private authService: AuthService) { }

  onSubmit() {
    this.authService.sendResetPasswordLink(this.email!).subscribe(
      response => {
        console.log('Reset link sent', response);
        alert('Reset link sent to email!');
      },
      error => {
        console.error('Error sending reset link', error);
      }
    );
  }
}
