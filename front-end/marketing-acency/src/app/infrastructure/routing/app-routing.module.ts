import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from 'src/app/feature-modules/layout/home/home.component';
import { LoginComponent } from '../auth/login/login.component';
import { RegistrationComponent } from '../auth/registration/registration.component';
import { ActivationComponent } from '../auth/activation/activation.component';
import { ForbiddenComponent } from '../auth/forbidden/forbidden.component';
import { EmployeeProfileComponent } from 'src/app/feature-modules/employee/employee-profile/employee-profile.component';
import { AuthGuard } from '../authorization/auth.guard';
import { PermissionPageComponent } from 'src/app/feature-modules/permission-page/permission-page.component';


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'activation/:tokenId', component: ActivationComponent},
  { path: '403', component: ForbiddenComponent},
  { path: 'employee-profile', component: EmployeeProfileComponent, canActivate:[AuthGuard], data:{role:['ROLE_ADMIN']}},
  { path: 'permission-page', component: PermissionPageComponent, canActivate:[AuthGuard], data:{role:['ROLE_ADMIN']}}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
