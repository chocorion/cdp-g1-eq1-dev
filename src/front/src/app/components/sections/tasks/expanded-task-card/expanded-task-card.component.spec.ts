import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpandedTaskCardComponent } from './expanded-task-card.component';

describe('ExpandedTaskCardComponent', () => {
  let component: ExpandedTaskCardComponent;
  let fixture: ComponentFixture<ExpandedTaskCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExpandedTaskCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpandedTaskCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
