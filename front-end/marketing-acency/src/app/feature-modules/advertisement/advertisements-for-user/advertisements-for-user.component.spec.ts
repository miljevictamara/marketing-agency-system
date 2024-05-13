import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementsForUserComponent } from './advertisements-for-user.component';

describe('AdvertisementsForUserComponent', () => {
  let component: AdvertisementsForUserComponent;
  let fixture: ComponentFixture<AdvertisementsForUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdvertisementsForUserComponent]
    });
    fixture = TestBed.createComponent(AdvertisementsForUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
