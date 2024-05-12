import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForbiddenPasswordlessLoginComponent } from './forbidden-passwordless-login.component';

describe('ForbiddenPasswordlessLoginComponent', () => {
  let component: ForbiddenPasswordlessLoginComponent;
  let fixture: ComponentFixture<ForbiddenPasswordlessLoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ForbiddenPasswordlessLoginComponent]
    });
    fixture = TestBed.createComponent(ForbiddenPasswordlessLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
