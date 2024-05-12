export interface Advertisement {
    id: number;
    clientId: number;
    slogan: string;
    duration: number;
    description: string;
    deadline: Date;
    active_from: Date;
    active_to: Date;
    request_description: string;
    status: AdvertisementStatus;
}

export enum AdvertisementStatus {
    PENDING,
    ACCEPTED
  }