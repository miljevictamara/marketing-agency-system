import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministratorProfileComponent } from './administrator-profile.component';

describe('AdministratorProfileComponent', () => {
  let component: AdministratorProfileComponent;
  let fixture: ComponentFixture<AdministratorProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdministratorProfileComponent]
    });
    fixture = TestBed.createComponent(AdministratorProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
