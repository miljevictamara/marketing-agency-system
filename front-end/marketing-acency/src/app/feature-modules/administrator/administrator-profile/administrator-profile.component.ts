import { Component, OnInit } from '@angular/core';
import { Administrator } from '../model/administrator.model';
import { AdministratorService } from '../administrator.service';
import { AuthService } from 'src/app/infrastructure/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-administrator-profile',
  templateUrl: './administrator-profile.component.html',
  styleUrls: ['./administrator-profile.component.css']
})
export class AdministratorProfileComponent implements OnInit {
  administrator: Administrator | undefined;
  editClicked: boolean = false;

  constructor(private router: Router, private administratorService: AdministratorService, private authService: AuthService) { }

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId();
    if (userId !== undefined) {
      this.administratorService.getAdministratorByUserId(userId).subscribe((administrator: any) => {
        this.administrator = administrator;
        console.log(administrator);
      });
    } else {
      console.error('User ID is undefined.');
    }
  }

  onEditClicked(administrator: Administrator) {
    this.administrator = administrator;
    this.editClicked = !this.editClicked;
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }
}
