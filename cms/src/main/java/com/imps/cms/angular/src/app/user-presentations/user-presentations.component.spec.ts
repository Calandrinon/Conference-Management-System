import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPresentationsComponent } from './user-presentations.component';

describe('UserPresentationsComponent', () => {
  let component: UserPresentationsComponent;
  let fixture: ComponentFixture<UserPresentationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPresentationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPresentationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
