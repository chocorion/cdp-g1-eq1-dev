import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsEditFormComponent } from './us-edit-form.component';

describe('UsEditFormComponent', () => {
  let component: UsEditFormComponent;
  let fixture: ComponentFixture<UsEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsEditFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
