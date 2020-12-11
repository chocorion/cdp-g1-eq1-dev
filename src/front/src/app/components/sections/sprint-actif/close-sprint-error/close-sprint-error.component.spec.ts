import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloseSprintErrorComponent } from './close-sprint-error.component';

describe('CloseSprintErrorComponent', () => {
  let component: CloseSprintErrorComponent;
  let fixture: ComponentFixture<CloseSprintErrorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CloseSprintErrorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CloseSprintErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
