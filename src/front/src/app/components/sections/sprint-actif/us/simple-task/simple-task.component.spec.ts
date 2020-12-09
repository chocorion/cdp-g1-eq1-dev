import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimpleTaskComponent } from './simple-task.component';

describe('SimpleTaskComponent', () => {
  let component: SimpleTaskComponent;
  let fixture: ComponentFixture<SimpleTaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimpleTaskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimpleTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
