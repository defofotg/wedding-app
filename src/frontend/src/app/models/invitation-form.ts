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
  invitationId: string;
  events: string[];
  firstName: string;
  lastName: string;
  secret: string;

}
