import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {InvitationDTO} from "../models/invitation-form";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) {}

  public exportInvitations(): Observable<Blob>{
    return this.http.get(environment.server + 'export', {responseType: 'blob'});
  }
}
