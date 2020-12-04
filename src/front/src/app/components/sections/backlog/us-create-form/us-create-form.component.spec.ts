import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsCreateFormComponent } from './us-create-form.component';

describe('UsCreateFormComponent', () => {
  let component: UsCreateFormComponent;
  let fixture: ComponentFixture<UsCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsCreateFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
