import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { AdministratorService } from '../administrator.service';
import { Administrator } from '../model/administrator.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-administrator-profile',
  templateUrl: './edit-administrator-profile.component.html',
  styleUrls: ['./edit-administrator-profile.component.css']
})
export class EditAdministratorProfileComponent implements OnChanges {

  constructor(private administratorService: AdministratorService) { }

  @Input() administrator: Administrator | undefined;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.administrator) {
      this.administratorForm.patchValue(this.administrator);
    }
  }

  administratorForm = new FormGroup ({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required),
    phoneNumber: new FormControl('', Validators.required)
  })

  editAdministratorProfile(): void {
    if (this.administratorForm.valid && this.administrator) {
      const updatedAdministrator: Administrator = { ...this.administrator, ...this.administratorForm.value };
      this.administratorService.updateAdministrator(updatedAdministrator).subscribe(
        (updated: Administrator) => {
          console.log('Administrator updated successfully:', updated);
        },
        (error) => {
          console.error('Failed to update administrator:', error);
        }
      );
    }
  }
}
