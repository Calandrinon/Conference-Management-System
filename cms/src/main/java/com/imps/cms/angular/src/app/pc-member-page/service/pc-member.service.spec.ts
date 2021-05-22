import { TestBed } from '@angular/core/testing';

import { PcMemberService } from './pc-member.service';

describe('PcMemberService', () => {
  let service: PcMemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PcMemberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
