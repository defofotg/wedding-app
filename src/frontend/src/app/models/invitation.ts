export interface Invitation  {
  login: string;
  password: string;
  status: InvitationStatus;
  events: Event[];
  guests: Guest[];
}

export interface InvitationTableItem  {
  mainGuest?: string;
  answer: string;
  attendantGuest?: string;
  mairie: string;
  eglise: string;
  vin: string;
  soiree: string;
}

export interface Guest  {
  fullname: string;
  status: GuestStatus;
}

export enum Event {
  MAIRIE = 'MAIRIE',
  EGLISE = 'EGLISE',
  VIN = 'VIN',
  SOIREE = 'SOIREE'
}

export enum GuestStatus {
  MAIN = 'MAIN',
  ATTENDANT = 'ATTENDANT',
}

export enum InvitationStatus {
  PENDING = 'EN ATTENTE',
  CONFIRMED = 'CONFIRMEE',
  DECLINED = 'REFUSEE'
}



