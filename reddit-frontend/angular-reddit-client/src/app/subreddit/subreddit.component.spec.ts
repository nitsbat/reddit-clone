import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubredditComponent } from './subreddit.component';

describe('SubredditComponent', () => {
  let component: SubredditComponent;
  let fixture: ComponentFixture<SubredditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubredditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubredditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
