import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {InvitationDTO} from "../models/invitation-form";
import {environment} from "../../environments/environment";
import {Invitation} from "../models/invitation";

const INVITATION_API = environment.server + 'invitations';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};
@Injectable({
  providedIn: 'root'
})
export class InvitationService {

  constructor(private http: HttpClient) {}

  public acceptInvitation(reply: InvitationDTO): Observable<any> {
    return this.http.post<InvitationDTO>(
      INVITATION_API + '/' + reply.invitationId +'/complete',
      reply,
      httpOptions
    );
  }

  public declineInvitation(invitationId: string): Observable<any> {
    return this.http.post<any>(
      INVITATION_API + '/' + invitationId +'/decline', {});
  }

  public retrieveInvitations(): Observable<Invitation[]> {
    return this.http.get<Invitation[]>(INVITATION_API);
  }
}
