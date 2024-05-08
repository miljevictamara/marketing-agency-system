import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Advertisement } from './model/advertisement.model';

@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {

  constructor(private http: HttpClient) { }

  getAdvertisements(): Observable<Advertisement[]> {
    return this.http.get<Advertisement[]>('https://localhost:8443/advertisement/accepted/');
  }

  getRequests(): Observable<Advertisement[]> {
    return this.http.get<Advertisement[]>('https://localhost:8443/advertisement/pending/');
  }
}
