import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AdvertisementService } from '../advertisement.service';
import { Advertisement, AdvertisementStatus } from '../model/advertisement.model';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { ClientService } from '../../client/client.service';

@Component({
  selector: 'app-advertisement-form',
  templateUrl: './advertisement-form.component.html',
  styleUrls: ['./advertisement-form.component.css']
})
export class AdvertisementFormComponent {

  constructor(private advertisementService: AdvertisementService, private authService: AuthService, private clientService: ClientService) { }

  advertisementForm = new FormGroup ({
    slogan: new FormControl('', Validators.required),
    duration: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    deadline: new FormControl('', Validators.required),
    activeFrom: new FormControl('', Validators.required),
    activeTo: new FormControl('', Validators.required),
    requestDescription: new FormControl('', Validators.required),
  });

  addAdvertisement(): void {
    if (this.advertisementForm.valid) {
      // Get current user id from AuthService
      const userId = this.authService.getCurrentUserId();

      // Get client information by user id from ClientService
      this.clientService.getClientByUserId(userId!).subscribe(client => {
        if (client) {
          const formData = this.advertisementForm.value;
      
          const duration = Number(formData.duration);
      
          const deadline = formData.deadline ? new Date(formData.deadline) : new Date();
          const activeFrom = formData.activeFrom ? new Date(formData.activeFrom) : new Date();
          const activeTo = formData.activeTo ? new Date(formData.activeTo) : new Date();
      
          const newAdvertisement: Advertisement = {
            id: 0, // RESITI
            client: client.id,
            slogan: formData.slogan!,
            duration: duration,
            description: formData.description!,
            deadline: deadline,
            active_from: activeFrom,
            active_to: activeTo,
            request_description: formData.requestDescription!,
            status: AdvertisementStatus.PENDING
          };
          
          this.advertisementService.createAdvertisement(newAdvertisement)
            .subscribe(() => {
              this.advertisementForm.reset();
            }, error => {
              // Handle error
            });
        } else {
          // Handle case when client is not found
        }
      }, error => {
        // Handle error
      });
    }
  }
}
