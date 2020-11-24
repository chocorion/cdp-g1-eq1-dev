import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnplannedComponent } from './unplanned.component';

describe('UnplanifiedComponent', () => {
  let component: UnplannedComponent;
  let fixture: ComponentFixture<UnplannedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnplannedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UnplannedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
