import { Component, ElementRef, OnInit, Renderer2, ViewChild, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { UserService } from 'src/app/feature-modules/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, AfterViewInit {
  form!: FormGroup;
  submitted = false;
  loginError = '';
  captchaResponse: string | null = null;
  siteKey: string = "6LcDAe8pAAAAAGZ3_jJjhzu8JXnYvjOMg6folgso";
  @ViewChild('recaptchaContainer') recaptchaContainer: ElementRef | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private userService: UserService,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      mail: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(64),
        ]),
      ],
      password: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(32),
        ]),
      ],
    });
  }

  ngAfterViewInit() {
    this.addRecaptchaScript();
  }

  private addRecaptchaScript() {
    const script = this.renderer.createElement('script');
    script.src = 'https://www.google.com/recaptcha/api.js';
    script.async = true;
    script.defer = true;
    this.renderer.appendChild(document.head, script);
  }

  onLoginClick() {
    if (this.recaptchaContainer) {
      this.recaptchaContainer.nativeElement.style.display = 'block';
      grecaptcha.render(this.recaptchaContainer.nativeElement, {
        sitekey: this.siteKey,
        callback: (response: string) => this.onCaptchaResolved(response),
        'expired-callback': () => this.onCaptchaExpired(),
        'error-callback': () => this.onCaptchaError()
      });
    }
  }

  onCaptchaResolved(response: string) {
    this.captchaResponse = response;
    this.onSubmit();
  }

  onCaptchaExpired() {
    this.captchaResponse = null;
    this.recaptchaContainer!.nativeElement.style.display = 'none';
  }

  onCaptchaError() {
    this.loginError = 'There was an error verifying the reCAPTCHA. Please try again.';
  }

  onSubmit() {
    if (!this.captchaResponse) {
      this.loginError = 'Please complete the reCAPTCHA';
      return;
    }

    const user = this.form.value;
    this.submitted = true;
    this.authService.login(user, this.captchaResponse).subscribe({
      next: data => {
        console.log(data);
        this.router.navigate(['/']);
      },
      error: error => {
        console.log(error);
        this.submitted = false;
        // Handle error messages or UI feedback
      }
    });
  }

  onPasswordlesLoginClick() {
    this.router.navigate(['passwordless-login']);
  }
}
