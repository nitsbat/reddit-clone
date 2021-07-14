import { NgModule } from '@angular/core';
import { LoginComponent } from './login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    LoginComponent,
  ],
  imports:[
      FormsModule,
    ReactiveFormsModule],
  exports : [
    LoginComponent
  ]
})
export class LoginModule { }