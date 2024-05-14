import { Component } from '@angular/core';
import { Advertisement, AdvertisementStatus } from '../model/advertisement.model';
import { AdvertisementService } from '../advertisement.service';
import { User } from 'src/app/infrastructure/auth/model/user.model';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { PermissionService } from '../../permission-page/permission.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent {
  requests: Advertisement[] | undefined;
  updateAdvertisment: boolean = false;
  user: User | undefined;

  constructor(private advertisementService: AdvertisementService, private authService: AuthService, private permission: PermissionService) { }

  ngOnInit(): void {
    this.getAdvertisement();
    this.authService.user$.subscribe(user => {
      this.user = user;
      this.permission.hasPermission(this.user.mail, 'UPDATE_ADVERTISMENT').subscribe(hasPermission => {
        this.updateAdvertisment = hasPermission;
      });

    });
  }

  getAdvertisement(): void {
    this.advertisementService.getRequests()
      .subscribe(request => this.requests = request);
  }

  onAcceptClicked(request: Advertisement) {
    this.advertisementService.getClientIdByAdvertismentId(request.id).subscribe(clientId => {
      const newAdvertisement: Advertisement = {
        id: request.id,
        client: clientId,
        slogan: request.slogan,
        duration: request.duration,
        description: request.description,
        deadline: request.deadline,
        active_from: request.active_from,
        active_to: request.active_to,
        request_description: request.request_description,
        status: AdvertisementStatus.ACCEPTED
      };
      // Call the update method from the service
      this.advertisementService.updateAdvertisement(newAdvertisement)
        .subscribe(updatedRequest => {
          // Do something with the updatedRequest if needed
        });
    });
  }
  
}
