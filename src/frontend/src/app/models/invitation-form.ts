export interface Invitation {
  isComing: string;
  hasGuest: string;
  guestFirstName: string;
  guestLastName: string;
  mairie: boolean;
  eglise: boolean;
  vin:boolean;
  soiree: boolean;
}

export interface Recapitulatif {
  invite: string;
  accompagnant?: string;
  events: string[];
}

export interface InvitationDTO {
  lastName: string;
  firstName: string;
  guestFirstName: string;
  guestLastName: string;
  events: string[];
  token: string;
  email: string;

}
