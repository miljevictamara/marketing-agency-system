import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit{
  form!: FormGroup;
  submitted = false;
  registrationError = '';

  constructor(private formBuilder: FormBuilder, 
              private authService: AuthService, 
              private router: Router, 
              private userService: UserService){
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      mail: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
    });
  }

  onSubmit() {
    this.submitted = true;

    this.authService.createUser(this.form.value)
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

}
