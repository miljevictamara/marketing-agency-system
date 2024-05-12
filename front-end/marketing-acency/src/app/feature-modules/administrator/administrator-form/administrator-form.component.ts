import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/infrastructure/auth/model/user.model';
import { Package } from '../../client/model/package.model';
import { RegistrationRequestStatus } from '../../user/model/registrationRequestStatus.model';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../../user/user.service';
import { AdministratorService } from '../administrator.service';
import { Administrator } from '../model/administrator.model';

@Component({
  selector: 'app-administrator-form',
  templateUrl: './administrator-form.component.html',
  styleUrls: ['./administrator-form.component.css']
})
export class AdministratorFormComponent {
  
  formUser!: FormGroup;
  formIndividual!: FormGroup;
  submitted = false;
  registrationError = '';
  shouldRenderIndividual:boolean = true;
  shouldRenderLegalEntity:boolean = false;
  shouldRenderThirdStepIndividual:boolean = false;
  shouldRenderLastStepIndividual:boolean = false;
  user!: User;
  firstName?: string | null;
  lastName?: string | null;
  companyName?: string | null;
  pib?: number | null;
  phoneNumber!: string;
  clientPackage!: Package;
  address!: string;
  city!: string;
  country!: string;
  isApproved!: RegistrationRequestStatus;
  userId!: number | undefined;

  constructor(private formBuilder: FormBuilder, 
              private authService: AuthService, 
              private router: Router, 
              private userService: UserService,
              private administratorService: AdministratorService){
  }

  ngOnInit(): void {
    this.formUser = this.formBuilder.group({
      mail: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      password: ['', Validators.compose([Validators.required, Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\\S+$).{8,}$')])],
      confirmationPassword: ['', Validators.compose([Validators.required, Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\\S+$).{8,}$')])]
    });


    this.formIndividual = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      address: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      phoneNumber: ['', Validators.required]
    });
  }

  registerIndividual()  {  
    this.shouldRenderIndividual = true;
    
  }

  registerLegalEntity()  {  
    this.shouldRenderLegalEntity = true;   
  }

  thirdStepIndividual()  {    

    //if (this.formUser.valid) {
      if (this.formUser.value.password !== this.formUser.value.confirmationPassword) {
        alert("Password and confirmation password do not match");
      }
   

      this.administratorService.createUser(
        this.user = this.formUser.value
      )
      .subscribe(
        (response) => {
          console.log('User saved!');
        },
        (error) => {
          // Handle reservation error
          if (error.status === 400) {
            alert('Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace.'); 
          } else if (error.status === 409) {
            alert('Password and confirmation password do not match.');
          }
          else if (error.status === 403) {
            alert('User has already been rejected. Please try again later.');
          }
          else if (error.status === 422) {
            alert('Email is already in use'); 
          } if (error.status === 201) {
            console.log('User saved!');
            this.shouldRenderThirdStepIndividual = true;
            this.shouldRenderIndividual = false;
          }
           else {
            alert('An unexpected error occurred.'); // or display an error message on the UI
          }
        }
      );

  
  }

  

  lastStepIndividual()  {  
    if (this.formIndividual.valid) {
      this.firstName = this.formIndividual.value.firstname;
      this.lastName = this.formIndividual.value.lastname;
      this.address = this.formIndividual.value.address;
      this.city = this.formIndividual.value.city;
      this.country = this.formIndividual.value.country;
      this.phoneNumber = this.formIndividual.value.phoneNumber;

      this.shouldRenderThirdStepIndividual = false;
      this.shouldRenderLastStepIndividual = true;
    } else{
      alert('Form is invalid. Please fill in all required fields.');
    }
       
  }


  

  submitIndividual() {
    this.userService.findUserIdByEmail(this.user.mail).subscribe(
      (user) => {
        this.userId = user.id;
  
        const administrator: Administrator = {
          user: { id: this.userId },
          firstName: this.formIndividual.value.firstname,
          lastName: this.formIndividual.value.lastname,
          phoneNumber: this.formIndividual.value.phoneNumber,
          address: this.formIndividual.value.address,
          city: this.formIndividual.value.city,
          country: this.formIndividual.value.country,
          id: 0
        };
      
        // Sada možete poslati ovaj objekt na vaš endpoint za registraciju klijenta
        this.administratorService.createAdministrator(administrator).subscribe(
          (response) => {
            console.log('Administrator uspešno registrovan!');
            // Dodajte logiku za preusmeravanje na odgovarajuću stranicu nakon registracije
          },
          (error) => {
            console.error('Greška prilikom registracije employee-a:', error);
            // Dodajte logiku za upravljanje greškama prilikom registracije
          }
        );
      },
      (error) => {
        console.error('Error finding user ID:', error);
        // Ovde možete dodati logiku za upravljanje greškama
      }
    );
  }
}
