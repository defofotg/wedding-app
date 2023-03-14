import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {UserInfo, UserTokenInfo} from "../models/user";
import {Invitation} from "../models/invitation-form";

const USER_KEY = 'auth-user';
const INVITE_KEY = 'invitation-form';
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private loggedInInfo: BehaviorSubject<boolean>;
  private userInfoSubject: BehaviorSubject<UserInfo>;

  constructor() {
    this.loggedInInfo = new BehaviorSubject<boolean>(this.isLoggedIn());
    this.userInfoSubject = new BehaviorSubject<UserInfo>(this.getUser());
  }

  public clean(): void {
    this.setValue(false);
    window.sessionStorage.clear();
    this.userInfoSubject.next({ firstName: '', lastName: '', token:'', email: '' });
  }

  public saveUser(user: UserTokenInfo): void {
    //this.setValue(true);
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    this.userInfoSubject.next(user?.userInfo);
  }

  public saveInvitationForm(invitationForm: Invitation): void {
    window.sessionStorage.removeItem(INVITE_KEY);
    window.sessionStorage.setItem(INVITE_KEY, JSON.stringify(invitationForm));
  }

  public getUser(): UserInfo {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return (JSON.parse(user) as UserTokenInfo)?.userInfo;
    }

    return {} as UserInfo;
  }

  public getInvitationForm(): Invitation {
    const invitation = window.sessionStorage.getItem(INVITE_KEY);
    if (invitation) {
      return JSON.parse(invitation) as Invitation;
    }

    return {} as Invitation;
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return true;
    }
    return false;
  }

  setValue(newValue: boolean): void {
    this.loggedInInfo.next(newValue);
  }
}
