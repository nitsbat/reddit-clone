import { NgModule } from '@angular/core';
import { SignUpComponent } from './sign-up.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    SignUpComponent,
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule],
  exports: [
    SignUpComponent,
  ]
})
export class SignUpModule { }