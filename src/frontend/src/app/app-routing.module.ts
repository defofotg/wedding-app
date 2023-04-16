import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginFormComponent} from "./login-form/login-form.component";
import {InvitationFormComponent} from "./invitation-form/invitation-form.component";
import {ConfirmationPageComponent} from "./confirmation-page/confirmation-page.component";
import {AuthGuard} from "./guards/auth.guard";
import {BackofficeComponent} from "./backoffice/backoffice.component";
import {AdminGuard} from "./guards/admin.guard";

const routes: Routes = [
  {
    path: 'login', component: LoginFormComponent
  },
  {
    path: 'invitation', component: InvitationFormComponent, canActivate: [AuthGuard]
  },
  {
    path: 'confirmation', component: ConfirmationPageComponent, canActivate: [AuthGuard]
  },
  {
    path: 'backoffice', component: BackofficeComponent, canActivate: [AdminGuard]
  },
  {
    path: '', redirectTo: 'login', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
