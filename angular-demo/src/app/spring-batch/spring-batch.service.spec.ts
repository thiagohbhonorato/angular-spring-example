import { TestBed } from '@angular/core/testing';

import { SpringBatchService } from './spring-batch.service';

describe('SpringBatchService', () => {
  let service: SpringBatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpringBatchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
