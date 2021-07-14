import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserProfileComponent } from './user-profile.component';

@NgModule({
  declarations: [
    UserProfileComponent,
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule],
  exports: [
    UserProfileComponent,
  ]
})
export class UserProfileModule { }