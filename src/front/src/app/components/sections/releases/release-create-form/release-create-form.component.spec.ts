import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleaseCreateFormComponent } from './release-create-form.component';

describe('ReleaseCreateFormComponent', () => {
  let component: ReleaseCreateFormComponent;
  let fixture: ComponentFixture<ReleaseCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReleaseCreateFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleaseCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
