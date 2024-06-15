import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TwoFAVerificationComponent } from './two-fa-verification.component';

describe('TwoFAVerificationComponent', () => {
  let component: TwoFAVerificationComponent;
  let fixture: ComponentFixture<TwoFAVerificationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TwoFAVerificationComponent]
    });
    fixture = TestBed.createComponent(TwoFAVerificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
