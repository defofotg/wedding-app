import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {InvitationDTO} from "../models/invitation-form";
import {environment} from "../../environments/environment";

const INVITATION_API = environment.server + 'invitation/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};
@Injectable({
  providedIn: 'root'
})
export class InvitationService {

  constructor(private http: HttpClient) { }

  public replyToInvitation(reply: InvitationDTO): Observable<any> {
    return this.http.post<any>(
      INVITATION_API + 'reply',
      reply,
      httpOptions
    );
  }
}
