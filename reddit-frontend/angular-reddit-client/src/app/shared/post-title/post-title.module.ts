import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PostTitleComponent } from './post-title.component';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { VoteModule } from '../vote-button/vote-button-module';

@NgModule({
  declarations: [
    PostTitleComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    FontAwesomeModule,
    VoteModule],
  exports: [
    PostTitleComponent,
  ]
})
export class PostTitleModule { }