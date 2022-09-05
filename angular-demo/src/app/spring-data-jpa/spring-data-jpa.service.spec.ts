import { TestBed } from '@angular/core/testing';

import { SpringDataJPAService } from './spring-data-jpa.service';

describe('PeopleListService', () => {
  let service: SpringDataJPAService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpringDataJPAService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
