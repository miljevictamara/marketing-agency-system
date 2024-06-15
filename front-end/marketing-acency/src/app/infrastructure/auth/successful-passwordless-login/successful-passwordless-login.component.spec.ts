import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessfulPasswordlessLoginComponent } from './successful-passwordless-login.component';

describe('SuccessfulPasswordlessLoginComponent', () => {
  let component: SuccessfulPasswordlessLoginComponent;
  let fixture: ComponentFixture<SuccessfulPasswordlessLoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuccessfulPasswordlessLoginComponent]
    });
    fixture = TestBed.createComponent(SuccessfulPasswordlessLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
