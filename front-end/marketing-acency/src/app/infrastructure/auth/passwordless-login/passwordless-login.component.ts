import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { AuthService } from '../auth.service';
import { User } from '../model/user.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-passwordless-login',
  templateUrl: './passwordless-login.component.html',
  styleUrls: ['./passwordless-login.component.css']
})
export class PasswordlessLoginComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  loginError = '';
  user!: User;

  constructor( private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private userService: UserService, private snackBar: MatSnackBar){
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      mail: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
    });
  }

  onSubmit() {
    
    this.userService.getUserByMail(this.form.value.mail).subscribe({
      next: (user: User) => {
        if (user) {
          this.userService.checkIfUserHasAppropriatePackage(user.mail).subscribe({
            next: (hasAppropriatePackage: boolean) => {
              if (hasAppropriatePackage) {
                this.showSendingEmail('Sending email...');
                this.authService.sendMail(this.form.value.mail).subscribe({
                  next: () => {
                    this.router.navigate(['/check-your-email']);
                  },
                  error: (error) => {
                    this.showNotification('You do not have permission for this.');
                  }
                });
              } else {
                this.showNotification('You do not have permission for this.');
              }
            },
            error: (error) => {
              console.error('Greška prilikom provere paketa:', error);
              this.showNotification('Error checking package.');
            }
          });
        }
      },
      error: (error) => {
        console.error('Greška prilikom dobijanja korisnika:', error);
        this.showNotification('You have entered an invalid email.');
      }
    });
    
  }
  
  showNotification(message: string): void {
    this.snackBar.open(message, 'OK', {
      duration: 3000, 
    });
  }

  showSendingEmail(message: string): void {
    this.snackBar.open(message, 'OK', {
      duration: 10000, 
    });
  }
}
