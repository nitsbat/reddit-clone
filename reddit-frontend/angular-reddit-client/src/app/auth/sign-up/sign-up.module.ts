import { NgModule } from '@angular/core';
import { SignUpComponent} from './sign-up.component';

@NgModule({
  declarations: [
    SignUpComponent,
  ],
  exports: [
    SignUpComponent,
  ]
})
export class SignUpModule { }