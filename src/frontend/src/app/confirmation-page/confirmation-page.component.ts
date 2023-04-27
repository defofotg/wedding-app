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

        this.invitation = this.storage.getInvitationForm();
        this.user = this.storage.getUser();

        if(params.present !== 'yes') {
          this.onDecline();
        } else {
          this.coming = params.present === 'yes';
          this.invitationRecap = {
            "invite" :  this.user.firstName + " " + this.user.lastName,
            "accompagnant" : this.invitation.guestFirstName + " " + this.invitation.guestLastName,
            "events" : this.getEvents(this.invitation)
          } as Recapitulatif;
        }
      });

  }

  getEvents(invitation: Invitation): Array<string> {
    let events = [];
    if (invitation != null) {
      if (invitation.mairie){
        events.push("MAIRIE");
      }
      if (invitation.eglise){
        events.push("EGLISE");
      }
      if (invitation.vin){
        events.push("VIN");
      }
      if (invitation.soiree){
        events.push("SOIREE");
      }
    }
    return events;
  }

  submit() {
    if(this.invitation && this.invitationRecap) {
      const invitationDTO = {
        "invitationId": this.user?.invitationId,
        "events" : this.invitationRecap?.events,
        "lastName" : this.invitation.guestLastName,
        "firstName" : this.invitation.guestFirstName,
        "secret" : this.user?.token
      } as InvitationDTO;

      this.invitationService.acceptInvitation(invitationDTO).subscribe({
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

  goBack() {
    this.router.navigate(['/invitation']);
  }

  onDecline(){
    this.invitationService.declineInvitation(this.user!.invitationId).subscribe({
        next: () => {
          this.storage.clean();
          this.toastrService.success('Formulaire soumis avec succès');
        },
        error: () => this.toastrService.error('Erreur lors de la soumission du formulaire')
      }
    );
  }
}
