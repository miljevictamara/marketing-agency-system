import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAdministratorProfileComponent } from './edit-administrator-profile.component';

describe('EditAdministratorProfileComponent', () => {
  let component: EditAdministratorProfileComponent;
  let fixture: ComponentFixture<EditAdministratorProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditAdministratorProfileComponent]
    });
    fixture = TestBed.createComponent(EditAdministratorProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
