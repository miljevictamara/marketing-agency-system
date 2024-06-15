import { Component } from '@angular/core';
import { User } from '../model/user.model';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { UserService } from 'src/app/feature-modules/user/user.service';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent {
  tokenId!: string;
  user!: User;
  hmac!: string;
  constructor(private route: ActivatedRoute, private service : UserService, private router: Router) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.tokenId = params['tokenId'];
      this.hmac = params['hmac'];
      

      this.service.getUserByToken(this.tokenId, this.hmac).subscribe(
        user => {
          this.user = user;
          this.activateUser();
        },
        error => {
          if (error.status === 403) {
            console.error('Forbidden', error);
            this.router.navigate(['/403']); 
          } else {
            console.error('Error fetching user:', error);
            this.router.navigate(['']); 
          }
        }
      );
    });
  }

  activateUser(): void {
    if (this.user) {
      this.service.updateIsActivated(this.user.id!).subscribe(
        (response) => {
          console.log('Aktivacija korisnika uspešna!', response);
        },
        (error) => {
          console.error('Greška prilikom aktivacije korisnika:', error);
        }
      );
    }
}

onLogin(): void {
  this.router.navigate(['/login']);
}

}
