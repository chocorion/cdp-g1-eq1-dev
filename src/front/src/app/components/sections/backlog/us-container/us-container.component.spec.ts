import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsContainerComponent } from './us-container.component';

describe('UsContainerComponent', () => {
  let component: UsContainerComponent;
  let fixture: ComponentFixture<UsContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
