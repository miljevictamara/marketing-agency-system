import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementFormComponent } from './advertisement-form.component';

describe('AdvertisementFormComponent', () => {
  let component: AdvertisementFormComponent;
  let fixture: ComponentFixture<AdvertisementFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdvertisementFormComponent]
    });
    fixture = TestBed.createComponent(AdvertisementFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
