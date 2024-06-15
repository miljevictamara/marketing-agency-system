import { Component } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-vpn',
  templateUrl: './vpn.component.html',
  styleUrls: ['./vpn.component.css']
})
export class VpnComponent {

  vpnResponse: string = "";

  constructor(private userService: UserService) { }

  testVpn() {
    this.userService.testVpn()
      .subscribe((data: any) => {
        this.vpnResponse = data.message; 
      }, (error) => {
        console.error('Error:', error);
        alert("VPN does not working.")
      });
  }

}
