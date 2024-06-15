import { Component } from '@angular/core';
import { Client } from '../model/client.model';
import { ClientService } from '../client.service';
import { UserService } from '../../user/user.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent {
  clients: Client[] | undefined;

  constructor(private clientService: ClientService, private userService: UserService) { }

  ngOnInit(): void {
    this.getClients();
  }

  getClients(): void {
    this.clientService.getClients()
      .subscribe(client => this.clients = client);
  }

  blockClient(userId: number): void {
    if (userId !== undefined) {
      this.userService.updateIsBlocked(userId).subscribe(
        response => {
          console.log('User blocked successfully', response);
          alert("Client blocked!")
        },
        error => {
          console.error('Error blocking user', error);
        }
      );
    }
  }
}
