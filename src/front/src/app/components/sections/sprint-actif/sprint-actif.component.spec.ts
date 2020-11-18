import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintActifComponent } from './sprint-actif.component';

describe('SprintActifComponent', () => {
  let component: SprintActifComponent;
  let fixture: ComponentFixture<SprintActifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintActifComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintActifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
