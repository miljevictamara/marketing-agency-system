import { Component } from '@angular/core';
import { Client } from '../model/client.model';
import { ClientService } from '../client.service';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.css']
})
export class ClientProfileComponent {
  client: Client | undefined;
  editClicked: boolean = false;

  constructor(private router: Router, private clientService: ClientService, private authService: AuthService) { }

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId();
    if (userId !== undefined) {
      this.clientService.getClientByUserId(userId).subscribe((client: any) => {
        this.client = client;
        console.log(client);
      });
    } else {
      console.error('User ID is undefined.');
    }
  }

  onEditClicked(client: Client) {
    this.client = client;
    this.editClicked = !this.editClicked;
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }
}
