import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { AuthService } from '../auth.service';
import { User } from '../model/user.model';
import { ClientType } from 'src/app/feature-modules/user/model/clientType.model';
import { RegistrationRequestStatus } from 'src/app/feature-modules/user/model/registrationRequestStatus.model';
import { Package } from 'src/app/feature-modules/user/model/package.model';
import { Client } from 'src/app/feature-modules/user/model/client.model';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit{
  formUser!: FormGroup;
  formIndividual!: FormGroup;
  submitted = false;
  registrationError = '';
  shouldRenderIndividual:boolean = false;
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
              private userService: UserService){
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
   

      this.authService.saveUser(
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


  addBasicPackage()  {   
    this.userService.getPackageByName('BASIC').subscribe(
      (packagee: Package) => {
        this.clientPackage = packagee;
        console.log('Basic Package:', this.clientPackage);
      },
      (error) => {
        console.error('Error fetching Basic Package:', error);
      }
    );
  }

  addStandardPackage()  {  
    this.userService.getPackageByName('STANDARD').subscribe(
      (packagee: Package) => {
        this.clientPackage = packagee;
        console.log('Basic Package:', this.clientPackage);
      },
      (error) => {
        console.error('Error fetching Standard Package:', error);
      }
    ); 
  }

  addGoldPackage()  { 
    this.userService.getPackageByName('GOLD').subscribe(
      (packagee: Package) => {
        this.clientPackage = packagee;
        console.log('Gold Package:', this.clientPackage);
      },
      (error) => {
        console.error('Error fetching Gold Package:', error);
      }
    );  
  }

  submitIndividual() {
    this.userService.findUserIdByEmail(this.user.mail).subscribe(
      (user) => {
        this.userId = user.id;
  
        const client: Client = {
          user: { id: this.userId },
          type: ClientType.INDIVIDUAL, 
          firstName: this.formIndividual.value.firstname,
          lastName: this.formIndividual.value.lastname,
          companyName: null,
          pib: null,
          clientPackage: this.clientPackage, 
          phoneNumber: this.formIndividual.value.phoneNumber,
          address: this.formIndividual.value.address,
          city: this.formIndividual.value.city,
          country: this.formIndividual.value.country,
          isApproved: RegistrationRequestStatus.PENDING 
        };
      
        // Sada možete poslati ovaj objekt na vaš endpoint za registraciju klijenta
        this.authService.registerClient(client).subscribe(
          (response) => {
            console.log('Klijent uspešno registrovan!');
            // Dodajte logiku za preusmeravanje na odgovarajuću stranicu nakon registracije
          },
          (error) => {
            console.error('Greška prilikom registracije klijenta:', error);
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
