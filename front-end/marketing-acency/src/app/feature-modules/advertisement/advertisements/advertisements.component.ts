import { Component, OnInit } from '@angular/core';
import { Advertisement } from '../model/advertisement.model';
import { AdvertisementService } from '../advertisement.service';

@Component({
  selector: 'app-advertisements',
  templateUrl: './advertisements.component.html',
  styleUrls: ['./advertisements.component.css']
})
export class AdvertisementsComponent implements OnInit {
  advertisements: Advertisement[] | undefined;

  constructor(private advertisementService: AdvertisementService) { }

  ngOnInit(): void {
    this.getAdvertisement();
  }

  getAdvertisement(): void {
    this.advertisementService.getAdvertisements()
      .subscribe(advertisement => this.advertisements = advertisement);
  }
}
