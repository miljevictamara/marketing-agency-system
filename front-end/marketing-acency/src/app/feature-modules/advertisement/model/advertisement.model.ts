import { Client } from "../../client/model/client.model";

export interface Advertisement {
    id: number;
    client: Client;
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