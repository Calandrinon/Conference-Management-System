import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChairPageComponent } from './chair-page.component';

describe('ChairPageComponent', () => {
  let component: ChairPageComponent;
  let fixture: ComponentFixture<ChairPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChairPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChairPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
