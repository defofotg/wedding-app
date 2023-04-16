import {Component, OnDestroy, OnInit} from '@angular/core';
import {FileService} from "../services/file.service";
import {catchError, takeUntil, tap} from "rxjs/operators";
import {EMPTY, Subject} from "rxjs";
import {ToastrService} from "ngx-toastr";
import {saveAs} from 'file-saver';
import {InvitationService} from "../services/invitation.service";
import {Event, GuestStatus, Invitation, InvitationTableItem} from "../models/invitation";

@Component({
  selector: 'app-backoffice',
  templateUrl: './backoffice.component.html',
  styleUrls: ['./backoffice.component.scss']
})
export class BackofficeComponent implements OnInit, OnDestroy{

  destroy$: Subject<boolean> = new Subject<boolean>();
  invitationTableItems: InvitationTableItem[] = [];
  constructor(
    private fileService: FileService,
    private invitationService: InvitationService,
    private toastrService: ToastrService) {}
  ngOnInit(): void {
    this.fetchInvitations();
  }
  public downloadCSV() {
    this.fileService.exportInvitations().pipe(
      takeUntil(this.destroy$),
      tap((response: Blob) => {
        saveAs(response, 'Rapport-invitations.xlsx');
      }),
      catchError(err => {
        this.toastrService.error('Error on Report file download.');
        return EMPTY;
      })
    ).subscribe()
  }

  private fetchInvitations() {
    this.invitationService.retrieveInvitations()
      .pipe(
        takeUntil(this.destroy$),
        tap(invitations => {
          this.invitationTableItems = invitations.map(invitation => this.toInvitationTableItem(invitation))
        })
      )
      .subscribe()
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    // Unsubscribe from the subject
    this.destroy$.unsubscribe();
  }

  private toInvitationTableItem(invitation: Invitation): InvitationTableItem {
    let invitationTableItem = {} as InvitationTableItem;

    invitationTableItem.mainGuest = invitation.guests.filter(guest => guest !== null)
      .find(guest => guest.status === GuestStatus.MAIN)?.fullname;

    invitationTableItem.answer = invitation.status.toString();

    invitationTableItem.attendantGuest = invitation.guests.filter(guest => guest !== null)
      .find(guest => guest.status === GuestStatus.ATTENDANT)?.fullname;

    invitationTableItem.mairie = invitation.events.includes(Event.MAIRIE) ? "OUI" : "NON";

    invitationTableItem.eglise = invitation.events.includes(Event.EGLISE) ? "OUI" : "NON";

    invitationTableItem.vin = invitation.events.includes(Event.VIN) ? "OUI" : "NON";

    invitationTableItem.soiree = invitation.events.includes(Event.SOIREE) ? "OUI" : "NON";

    return invitationTableItem;
  }

}
