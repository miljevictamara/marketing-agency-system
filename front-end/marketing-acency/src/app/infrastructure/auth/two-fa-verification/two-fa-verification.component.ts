import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-two-fa-verification',
  templateUrl: './two-fa-verification.component.html',
  styleUrls: ['./two-fa-verification.component.css']
})
export class TwoFAVerificationComponent {
  form!: FormGroup;
  submitted = false;
  verificationError = '';
  mail!: string;

  constructor( private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private userService: UserService, private route: ActivatedRoute){
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      code: ['', Validators.compose([Validators.required])],
    });

    this.route.params.subscribe(params => {
      this.mail = params['mail'];
    });
  }

  onSubmit() {
    this.submitted = true;

    const verifyCode = {
      mail: this.mail,
      code: this.form.value.code
    };

    this.authService.verifyCode(verifyCode).subscribe(
      (res: any) => {
        console.log('Response:', res); 
        this.router.navigate(['/']);
      },
      (error: any) => {
        console.error('Error:', error);
        this.submitted = false;
        this.verificationError = 'Verification failed. Please try again.';
        alert("Invalid verification code.")
      }
    );


  }
}
