import { NumberValueAccessor } from "@angular/forms";
import { User } from "src/app/infrastructure/auth/model/user.model";
import { Package } from "./package.model";

export interface Client {
    id: number;
    user: User;
    clientType: ClientType;
    firstName?: string | null;
    lastName?: string | null;
    companyName?: string | null;
    pib?: number;
    clientPackage: Package;
    phoneNumber?: string | null;
    address?: string | null;
    city?: string | null;
    country?: string | null;
    isApproved?: RegistrationRequestStatus;
}

export enum ClientType {
    INDIVIDUAL,
    LEGAL_ENTITY
}

export enum RegistrationRequestStatus {
    PENDING,
    APPROVED,
    REJECTED
}