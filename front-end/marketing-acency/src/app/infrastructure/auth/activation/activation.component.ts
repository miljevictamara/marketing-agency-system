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
      this.route.queryParams.subscribe(params => {
        this.hmac = params['hmac'];
      });

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
         
          }
        }
      );
    });
  }

  activateUser(): void {
    if (this.user) {
      this.service.updateIsActivated(this.user.id!).subscribe(
      
      );
    }
  }
}
