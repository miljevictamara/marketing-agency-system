import { Component } from '@angular/core';
import { Client } from '../model/client.model';
import { ClientService } from '../client.service';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { PermissionService } from '../../permission-page/permission.service';
import { User } from 'src/app/infrastructure/auth/model/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.css']
})
export class ClientProfileComponent {
  client: Client | undefined;
  editClicked: boolean = false;
  addAdvertisementClicked: boolean = false;
  user: User | undefined;

  hasEmployeePermission: boolean = false;
  getByUserId: boolean = false;
  updateClient: boolean = false;
  createAdvertisment: boolean = false;
  advByClient: boolean = false;
  
  constructor(private router: Router, private clientService: ClientService, private authService: AuthService, private permission: PermissionService) { }

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

    this.authService.user$.subscribe(user => {
      this.user = user;
      this.permission.hasPermission(this.user.mail, 'GET__ADVERTISMENTS_BY_CLIENT').subscribe(hasPermission => {
        this.advByClient = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'GET_CLIENT_BYUSERID').subscribe(hasPermission => {
        this.getByUserId = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'CREATE_ADVERTISMENT').subscribe(hasPermission => {
        this.createAdvertisment = hasPermission;
      });
      this.permission.hasPermission(this.user.mail, 'UPDATE_CLIENT').subscribe(hasPermission => {
        this.updateClient = hasPermission;
      });
    });
  }

  onEditClicked(client: Client) {
    this.client = client;
    this.editClicked = !this.editClicked;
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }
}
