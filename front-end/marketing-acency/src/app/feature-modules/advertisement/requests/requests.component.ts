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
    // Update the status to ACCEPTED
    request.status = AdvertisementStatus.ACCEPTED;

    // Call the update method from the service
    this.advertisementService.updateAdvertisement(request)
      .subscribe(updatedRequest => {
        alert('Request for an advertisement accepted!');
      });
  }
}
