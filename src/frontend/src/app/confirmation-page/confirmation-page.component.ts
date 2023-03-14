import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {filter} from "rxjs/operators";
import {StorageService} from "../services/storage.service";
import {Invitation, InvitationDTO, Recapitulatif} from "../models/invitation-form";
import {InvitationService} from "../services/invitation.service";
import {ToastrService} from "ngx-toastr";
import {UserInfo} from "../models/user";

@Component({
  selector: 'app-confirmation-page',
  templateUrl: './confirmation-page.component.html',
  styleUrls: ['./confirmation-page.component.scss']
})
export class ConfirmationPageComponent implements OnInit {
  coming = false;
  invitation: Invitation | undefined;

  user: UserInfo | undefined;
  invitationRecap: Recapitulatif | undefined;
  constructor(
    readonly router: Router,
    private route: ActivatedRoute,
    private storage: StorageService,
    private toastrService: ToastrService,
    readonly invitationService: InvitationService
  ) {


  }
  ngOnInit(): void {
    this.route.queryParams
      .pipe(filter(params => params.present))
      .subscribe(params => {
        this.coming = params.present === 'yup';
        this.invitation = this.storage.getInvitationForm();
        this.user = this.storage.getUser();
        this.invitationRecap = {
          "invite" :  this.user.firstName + " " + this.user.lastName,
          "accompagnant" : this.invitation.guestFirstName + " " + this.invitation.guestLastName,
          "events" : this.getEvents(this.invitation)
        } as Recapitulatif;
      });

  }

  getEvents(invitation: Invitation): Array<string> {
    let events = [];
    if (invitation != null) {
      if (invitation.mairie){
        events.push("Mairie");
      }
      if (invitation.eglise){
        events.push("Eglise");
      }
      if (invitation.vin){
        events.push("Vin");
      }
      if (invitation.soiree){
        events.push("Soirée");
      }
    }
    return events;
  }

  onClick() {
    if(this.invitation && this.invitationRecap) {
      const invitationDTO = {
        "lastName": this.user?.lastName,
        "firstName": this.user?.firstName,
        "guestLastName" : this.invitation.guestLastName,
        "guestFirstName" : this.invitation.guestFirstName,
        "events" : this.invitationRecap?.events,
        "token" : this.user?.token,
        "email" : this.user?.email
      } as InvitationDTO;

      this.invitationService.replyToInvitation(invitationDTO).subscribe({
          next: () => {
            this.storage.clean();
            this.toastrService.success('Formulaire soumis avec succès');
            this.router.navigate(['/']);
          },
          error: () => this.toastrService.error('Erreur lors de la soumission du formulaire')
        }
      );
    }
  }

  onDecline(){
    this.router.navigate(
      ['/invitation']);
  }
}
