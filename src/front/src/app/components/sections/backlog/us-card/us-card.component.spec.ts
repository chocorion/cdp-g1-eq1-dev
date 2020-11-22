import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsCardComponent } from './us-card.component';

describe('UsCardComponent', () => {
  let component: UsCardComponent;
  let fixture: ComponentFixture<UsCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
