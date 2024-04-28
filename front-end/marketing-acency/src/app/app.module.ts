import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavbarComponent } from './feature-modules/layout/navbar/navbar.component';
import { LayoutModule } from './feature-modules/layout/layout.module';
import { FooterComponent } from './feature-modules/layout/footer/footer.component';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RoutingModule } from './infrastructure/routing/routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TokenInterceptor } from './infrastructure/auth/interceptor/TokenInterceptor';
import { AuthModule } from './infrastructure/auth/auth.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    LayoutModule,
    RouterModule,
    AuthModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule
  ],
  providers: [ 
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,     
    },
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
