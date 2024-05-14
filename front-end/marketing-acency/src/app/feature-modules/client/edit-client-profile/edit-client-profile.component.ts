import { Component, Input, SimpleChanges } from '@angular/core';
import { ClientService } from '../client.service';
import { Client } from '../model/client.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-client-profile',
  templateUrl: './edit-client-profile.component.html',
  styleUrls: ['./edit-client-profile.component.css']
})
export class EditClientProfileComponent {
  constructor(private clientService: ClientService) { }

  @Input() client: Client | undefined;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.client) {
      this.clientForm.patchValue(this.client);
    }
  }

  clientForm = new FormGroup ({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required),
    phoneNumber: new FormControl('', Validators.required)
  })

  editClientProfile(): void {
    if (this.clientForm.valid && this.client) {
      const updatedClient: Client = { ...this.client, ...this.clientForm.value };
      this.clientService.updateClient(updatedClient).subscribe(
        (updated: Client) => {
          console.log('Client updated successfully:', updated);
          alert('Client profile updated successfully!');
        },
        (error) => {
          console.error('Failed to update employee:', error);
        }
      );
    }
  }
}
