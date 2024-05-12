import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { UserService } from 'src/app/feature-modules/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  loginError = '';

  constructor( private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private userService: UserService){
  }
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      mail: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])],
      confirmationPassword: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
    });
  }
  onSubmit() {
    this.submitted = true;

    this.authService.login(this.form.value)
      .subscribe({
        next: data => {
          console.log(data);
          this.router.navigate(['/']);
        },
        error: error => {
          console.log(error);
          this.submitted = false;
        }
      });


  }

  onPasswordlesLoginClick(){
    this.router.navigate(['passwordless-login']);
  }

}
