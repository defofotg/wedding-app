import { Component } from '@angular/core';
import {Invitation} from "../models/invitation-form";
import {Router} from "@angular/router";
import {StorageService} from "../services/storage.service";

@Component({
  selector: 'app-invitation-form',
  templateUrl: './invitation-form.component.html',
  styleUrls: ['./invitation-form.component.scss']
})
export class InvitationFormComponent {

  isComing: string = "";
  hasGuest: string = "";

  willCome = false;
  withSomebody = false;
  okMairie = true;
  okEglise = true;
  okVin = true;
  okSoiree = true;
  constructor(readonly router: Router, readonly storageService: StorageService) {}
  onSubmit(form: any) {
    if (form?.form?.invalid) {
      return;
    }

    let invitationFormContent = form?.form?.value as Invitation;

    if (invitationFormContent.isComing === 'false') {
      this.router.navigate(
        ['/confirmation'],
        { queryParams: { present: 'no' } });
    } else {
      this.storageService.saveInvitationForm(invitationFormContent);
      this.router.navigate(
        ['/confirmation'],
        { queryParams: { present: 'yes' } });
    }
  }

 changeResponse() {
    this.willCome = this.isComing === 'true';
 }
  changeGuestResponse() {
    this.withSomebody = this.hasGuest === 'true';
 }
}
