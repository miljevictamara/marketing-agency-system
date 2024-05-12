import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-passwordless-login',
  templateUrl: './passwordless-login.component.html',
  styleUrls: ['./passwordless-login.component.css']
})
export class PasswordlessLoginComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  loginError = '';

  constructor( private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private userService: UserService){
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      mail: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
    });
  }

  onSubmit() {
    this.submitted = true;
  
    this.authService.sendMail(this.form.value.mail)
      .subscribe({
        next: () => {
          this.router.navigate(['/check-your-email']);
        },
        error: (error) => {
          this.router.navigate(['/forbidden-passwordless-login']);
        }
      });
  }
  
}
