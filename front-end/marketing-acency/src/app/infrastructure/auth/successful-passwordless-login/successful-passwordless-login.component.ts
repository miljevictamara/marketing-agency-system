import { Component, OnInit } from '@angular/core';
import { User } from '../model/user.model';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/feature-modules/user/user.service';
import { AuthService } from '../auth.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-successful-passwordless-login',
  templateUrl: './successful-passwordless-login.component.html',
  styleUrls: ['./successful-passwordless-login.component.css']
})
export class SuccessfulPasswordlessLoginComponent  implements OnInit{
  tokenId!: string;
  user!: User;

  constructor(private route: ActivatedRoute, private service : UserService, private router: Router, private auth: AuthService) {}

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap(params => {
        this.tokenId = params['tokenId'];
        return this.service.getUserByLoginToken(this.tokenId);
      })
    ).subscribe({
      next: user => {
        this.user = user;
        this.auth.passwordlessLogin(this.user);
        this.router.navigate(['']); 
      },
      error: error => {
        console.error('Forbidden', error);
        this.router.navigate(['/forbidden-passwordless-login']); 
      }
    });
  }

}
