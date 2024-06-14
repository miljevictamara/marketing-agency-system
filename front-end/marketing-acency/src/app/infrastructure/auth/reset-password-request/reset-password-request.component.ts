import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-reset-password-request',
  template: `
    <form (ngSubmit)="onSubmit()">
      <label for="email">Email:</label>
      <input type="email" id="email" [(ngModel)]="email" name="email" required>
      <button type="submit">Send Reset Link</button>
    </form>
  `
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
