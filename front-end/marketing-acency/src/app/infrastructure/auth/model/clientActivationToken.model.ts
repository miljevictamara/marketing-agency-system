import { User } from "./user.model";

export interface ClientActivationToken {
    id: string;
    user: User;
    creationDate: Date;
    duration: number;
}