import { Component } from '@angular/core';
import { Advertisement, AdvertisementStatus } from '../model/advertisement.model';
import { AdvertisementService } from '../advertisement.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent {
  requests: Advertisement[] | undefined;

  constructor(private advertisementService: AdvertisementService) { }

  ngOnInit(): void {
    this.getAdvertisement();
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
