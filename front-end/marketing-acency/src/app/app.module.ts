import { NgModule, importProvidersFrom } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { environment } from '../environments/environment';

import { AppComponent } from './app.component';
import { NavbarComponent } from './feature-modules/layout/navbar/navbar.component';
import { LayoutModule } from './feature-modules/layout/layout.module';
import { FooterComponent } from './feature-modules/layout/footer/footer.component';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './infrastructure/routing/app-routing.module';
import { HomeComponent } from './feature-modules/layout/home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthModule } from './infrastructure/auth/auth.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { JwtInterceptor } from '@auth0/angular-jwt';
import { AuthService } from './infrastructure/auth/auth.service';
import { TokenInterceptor } from './infrastructure/auth/interceptor/TokenInterceptor';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { EmployeeModule } from './feature-modules/employee/employee.module';
import { AdministratorModule } from './feature-modules/administrator/administrator.module';
import { PermissionPageModule } from './feature-modules/permission-page/permission-page.module';
import { RegistrationRequestComponent } from './feature-modules/administrator/registration-request/registration-request.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { RecaptchaModule, RecaptchaFormsModule, RecaptchaV3Module, RECAPTCHA_V3_SITE_KEY } from "ng-recaptcha";
import { RECAPTCHA_SETTINGS,RecaptchaSettings } from 'ng-recaptcha';
import { NgxCaptchaModule } from 'ngx-captcha';
import { WebSocketService } from './infrastructure/auth/service/socket.service';
import { LogMessagesComponent } from './infrastructure/monitoring/log-messages/log-messages.component';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
@NgModule({
  declarations: [
    AppComponent,
    RegistrationRequestComponent,
    LogMessagesComponent,
  ],
  imports: [
    BrowserModule,
    LayoutModule,
    RouterModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatSnackBarModule,
    EmployeeModule,
    AdministratorModule,
    EmployeeModule,
    PermissionPageModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    RecaptchaModule,
    RecaptchaFormsModule,
    FormsModule,
    NgxCaptchaModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
  ],
  providers: [ 
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,     
    },
    {
      provide: RECAPTCHA_SETTINGS,
      useValue: {
        siteKey: environment.recaptcha.siteKey,
      } as RecaptchaSettings,
    },    
    WebSocketService
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
