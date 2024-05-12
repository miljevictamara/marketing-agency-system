import { Component } from '@angular/core';
import { Advertisement } from '../model/advertisement.model';
import { AdvertisementService } from '../advertisement.service';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';

@Component({
  selector: 'app-advertisements-for-user',
  templateUrl: './advertisements-for-user.component.html',
  styleUrls: ['./advertisements-for-user.component.css']
})
export class AdvertisementsForUserComponent {
  advertisements: Advertisement[] | undefined;

  constructor(private advertisementService: AdvertisementService, private authService: AuthService) { }

  ngOnInit(): void {
    this.getAdvertisement();
  }

  getAdvertisement(): void {
    const clientUserId = this.authService.getCurrentUserId();
    this.advertisementService.getAdvertisementsForUser(clientUserId!)
      .subscribe(advertisement => this.advertisements = advertisement);
  }
}
