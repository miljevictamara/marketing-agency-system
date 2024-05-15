import { Component, OnInit } from '@angular/core';
import { Client } from '../../user/model/client.model';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../../user/user.service';
import { User } from 'src/app/infrastructure/auth/model/user.model';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
  

  constructor(private authService: AuthService, 
              private router: Router,
              private userService: UserService,
              private formBuilder: FormBuilder){

                this.addReasonForm = this.formBuilder.group({
                  reason: ['', Validators.required] 
                });  
  }

  ngOnInit(): void {
    this.loadIndividuals();
    this.loadLegalEntities();
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
}
