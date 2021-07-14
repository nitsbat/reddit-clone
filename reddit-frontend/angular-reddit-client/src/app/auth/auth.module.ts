import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignUpModule } from './sign-up/sign-up.module';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginModule } from './login/login.module';
import { UserProfileModule } from './user-profile/user-profile-module';

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    LoginModule,
    SignUpModule,
    UserProfileModule
  ]
})
export class AuthModule { }