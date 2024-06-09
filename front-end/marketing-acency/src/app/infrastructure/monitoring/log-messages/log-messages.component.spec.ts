import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogMessagesComponent } from './log-messages.component';

describe('LogMessagesComponent', () => {
  let component: LogMessagesComponent;
  let fixture: ComponentFixture<LogMessagesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogMessagesComponent]
    });
    fixture = TestBed.createComponent(LogMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
