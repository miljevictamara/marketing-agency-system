import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { AuthService } from '../auth.service';
import { User } from '../model/user.model';

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

  constructor(private formBuilder: FormBuilder, 
              private authService: AuthService, 
              private router: Router, 
              private userService: UserService){
  }

  ngOnInit(): void {
    this.formUser = this.formBuilder.group({
      mail: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      password: ['', Validators.compose([Validators.required, Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\\S+$).{8,}$')])],
      confirmPassword: ['', Validators.compose([Validators.required, Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\\S+$).{8,}$')])]
    });
  }

  registerIndividual()  {  
    this.shouldRenderIndividual = true;
    
  }

  registerLegalEntity()  {  
    this.shouldRenderLegalEntity = true;   
  }

  thirdStepIndividual()  {  
    

    // Provera da li se lozinka poklapa sa potvrdom lozinke
    if (this.formUser.value.password !== this.formUser.value.confirmPassword) {
      this.registrationError = 'Password and confirmation password do not match';
      return;
    }

    //Create user
    const user: User = {
      mail: this.formUser.value.name || "",
      password: this.formUser.value.password || "",
      confirmationPassword: this.formUser.value.confirmationPassword || "",
      isBlocked: false,
      isActivated: false,
      roles: [] ///PROVERIIII
    };



  // Provera da li je email već u upotrebi
  this.authService.findByMail(this.formUser.value.mail).subscribe(
    (existingUser) => {
      if (existingUser) {
        this.registrationError = 'Email is already in use';
        return;
      }

      // Validacija lozinke prema zadatom regexu
      const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\S+$).{8,}$/;
      if (!passwordRegex.test(this.formUser.value.password)) {
        this.registrationError = 'Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace.';
        alert(this.registrationError);
        return;
      }

      // Ako su svi uslovi zadovoljeni, šaljemo podatke na server
      this.authService.createUser(this.formUser.value).subscribe(
        (response) => {
          // Uspesno registrovanje korisnika
          this.shouldRenderThirdStepIndividual = true;
          this.shouldRenderIndividual = false; 
        },
        (error) => {
          // Greška prilikom registrovanja korisnika
          alert(this.registrationError);
          this.registrationError = 'Failed to register user. Please try again later.';
        }
      );
    },
    (error) => {
      // Greška prilikom provere da li je email u upotrebi
      alert(this.registrationError);
      this.registrationError = 'Failed to check email availability. Please try again later.';
    }
  );
  }

  lastStepIndividual()  {  
    this.shouldRenderThirdStepIndividual = false;
    this.shouldRenderLastStepIndividual = true;   
  }


  addBronzePackage()  {   
  }

  addSilverPackage()  {   
  }

  addGoldPackage()  {   
  }

  submit() {

  }

}
