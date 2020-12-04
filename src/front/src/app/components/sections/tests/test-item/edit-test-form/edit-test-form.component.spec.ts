import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTestFormComponent } from './edit-test-form.component';

describe('EditTestFormComponent', () => {
  let component: EditTestFormComponent;
  let fixture: ComponentFixture<EditTestFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTestFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
