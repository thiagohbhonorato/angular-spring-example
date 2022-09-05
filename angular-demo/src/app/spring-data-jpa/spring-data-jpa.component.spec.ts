import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpringDataJpaComponent } from './spring-data-jpa.component';

describe('SpringDataJpaComponent', () => {
  let component: SpringDataJpaComponent;
  let fixture: ComponentFixture<SpringDataJpaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpringDataJpaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpringDataJpaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
