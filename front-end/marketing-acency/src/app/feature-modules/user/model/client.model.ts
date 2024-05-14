import { User } from "src/app/infrastructure/auth/model/user.model";
import { ClientType } from "./clientType.model";
import { RegistrationRequestStatus } from "./registrationRequestStatus.model";
import { Package } from "./package.model";

export interface Client {
    id?: number; 
    user: String ;
    type: ClientType;
    firstName?: string | null;
    lastName?: string | null;
    companyName?: string | null;
    pib?: number | null;
    clientPackage:String ;
    phoneNumber: string;
    address: string;
    city: string;
    country: string;
    isApproved: RegistrationRequestStatus;
}