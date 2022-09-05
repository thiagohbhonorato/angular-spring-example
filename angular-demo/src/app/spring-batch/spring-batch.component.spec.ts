import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpringBatchComponent } from './spring-batch.component';

describe('SpringBatchComponent', () => {
  let component: SpringBatchComponent;
  let fixture: ComponentFixture<SpringBatchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpringBatchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpringBatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
