import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PcMemberPageComponent } from './pc-member-page.component';

describe('PcMemberPageComponent', () => {
  let component: PcMemberPageComponent;
  let fixture: ComponentFixture<PcMemberPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PcMemberPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PcMemberPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
