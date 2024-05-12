import { Component } from '@angular/core';
import { Client } from '../model/client.model';
import { ClientService } from '../client.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent {
  clients: Client[] | undefined;

  constructor(private clientService: ClientService) { }

  ngOnInit(): void {
    this.getClients();
  }

  getClients(): void {
    this.clientService.getClients()
      .subscribe(client => this.clients = client);
  }
}
