import { Component, OnInit } from '@angular/core';
import { Client } from '../../user/model/client.model';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../../user/user.service';
import { User } from 'src/app/infrastructure/auth/model/user.model';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PermissionService } from '../../permission-page/permission.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-registration-request',
  templateUrl: './registration-request.component.html',
  styleUrls: ['./registration-request.component.css']
})
export class RegistrationRequestComponent implements OnInit {
  individuals: any[] = [];
  legalEntities: any[] = [];
  user!: User;
  userId!: number | undefined;
  shouldRenderAddRejectionNote:boolean = false;
  reason: string = '';
  addReasonForm: FormGroup;
  selectedClient: any;
  
  registrationRequests: boolean = false;
  registrationLegalRequests: boolean = false;
  approve: boolean = false;
  reject: boolean = false;

  constructor(private authService: AuthService, 
              private router: Router,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private permission: PermissionService,
              private snackBar: MatSnackBar){

                this.addReasonForm = this.formBuilder.group({
                  reason: ['', Validators.required] 
                });  
  }

  ngOnInit(): void {
    this.loadIndividuals();
    this.loadLegalEntities();
    this.authService.user$.subscribe(user => {
      this.user = user;
      this.permission.hasPermission(this.user.mail, 'VIEW_ALL_INDIVIDUALS').subscribe(hasPermission => {
        this.registrationRequests = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'VIEW_ALL_LEGAL_ENTITIES').subscribe(hasPermission => {
        this.registrationLegalRequests = hasPermission;
      });

      this.permission.hasPermission(this.user.mail, 'APROVE_REGISTRATION_REQUEST').subscribe(hasPermission => {
        this.approve = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'REJECT_REGISTRATION_REQUEST').subscribe(hasPermission => {
        this.reject = hasPermission;
      });
      
    });
  }

  getEmail(individual: any): Observable<string> {
    return this.userService.findByUserId(individual.user.id!).pipe(
      map((user: User) => user.mail)
    );
  }

  onRejectForm(client: any){
    this.shouldRenderAddRejectionNote = true;
    this.selectedClient = client;
  }

  onApproveClicked(individual: any){
    this.userId = individual.id;
    this.showSendingEmail('Sending email...');

    this.userService.approveRegistration(this.userId!).subscribe({
      next: () => {
        console.log('Registration confirmed!');
        this.loadIndividuals();
        this.loadLegalEntities();
      },
      error: (error) => {
        console.error('Error approving registration:', error);
      }
    });
  }

  onRejectClicked(){
    this.shouldRenderAddRejectionNote = false;
    this.reason =  this.addReasonForm.value.reason;
    this.showSendingEmail('Sending email...');

    this.userService.rejectRegistration(this.selectedClient!.id, this.reason).subscribe({
      next: () => {
        console.log('Registration rejected!');
        this.loadIndividuals();
        this.loadLegalEntities();
        this.addReasonForm.reset();
      },
      error: (error) => {
        console.error('Error rejecting registration:', error);
      }
    });
  }

  loadIndividuals() {
    this.userService.getAllIndividuals().subscribe((individuals: Client[]) => {
      this.individuals = individuals;
    });
  }

  loadLegalEntities() {
    this.userService.getAllLegalEntities().subscribe((legalEntities: Client[]) => {
      this.legalEntities = legalEntities;
    });
  }

  onCloseFormClicked(): void {
    this.shouldRenderAddRejectionNote = !this.shouldRenderAddRejectionNote;
  }

  showSendingEmail(message: string): void {
    this.snackBar.open(message, 'OK', {
      duration: 10000, 
    });
  }
}
