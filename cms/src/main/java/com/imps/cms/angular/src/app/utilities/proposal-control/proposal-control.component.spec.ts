import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProposalControlComponent } from './proposal-control.component';

describe('ProposalControlComponent', () => {
  let component: ProposalControlComponent;
  let fixture: ComponentFixture<ProposalControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProposalControlComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProposalControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
